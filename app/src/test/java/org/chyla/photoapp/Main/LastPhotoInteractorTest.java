package org.chyla.photoapp.Main;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Main.Model.LastPhotoInteractor;
import org.chyla.photoapp.Main.Model.LastPhotoInteractorImpl;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class LastPhotoInteractorTest extends BaseTest {

    @Mock
    DatabaseRepository repository;

    @Mock
    Authenticator authenticator;

    final User loggedUser = new User("USER_ID1", "user@example.com");

    LastPhotoInteractor interactor;

    @Override
    public void setUp() {
        super.setUp();

        interactor = new LastPhotoInteractorImpl(authenticator, repository);
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(authenticator);
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testConstructor() {
        // empty body
    }

    @Test
    public void testGetLastPhoto() {
        when(authenticator.getLoggedUser()).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return loggedUser;
            }
        });

        interactor.getLastPhoto();

        verify(authenticator, times(1)).getLoggedUser();
        verify(repository, times(1)).getLastPhoto(loggedUser);
    }

}
