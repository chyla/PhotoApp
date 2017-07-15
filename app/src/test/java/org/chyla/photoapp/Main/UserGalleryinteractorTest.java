package org.chyla.photoapp.Main;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.Main.Model.UserGalleryInteractor;
import org.chyla.photoapp.Main.Model.UserGalleryInteractorImpl;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class UserGalleryinteractorTest extends BaseTest {

    @Mock
    DatabaseRepository localDatabase;

    @Mock
    CloudDatabaseRepository cloudDatabase;

    @Mock
    Authenticator authenticator;

    final User loggedUser = new User("USER_ID1", "user@example.com");

    UserGalleryInteractor interactor;

    @Override
    public void setUp() {
        super.setUp();

        interactor = new UserGalleryInteractorImpl(authenticator, cloudDatabase, localDatabase);

        when(authenticator.getLoggedUser()).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return loggedUser;
            }
        });
    }

    @After
    public void tearDown() {
        verifyNoMoreInteractions(authenticator);
        verifyNoMoreInteractions(localDatabase);
        verifyNoMoreInteractions(cloudDatabase);
    }

    @Test
    public void testConstructor() {
        // empty body
    }

    @Test
    public void testAddPhotoToGallery() throws MalformedURLException {
        final Photo newPhoto = new Photo("title", "descr", new URL("http://example.com"));

        interactor.addPhotoToGallery(newPhoto);

        verify(authenticator, times(1)).getLoggedUser();
        verify(localDatabase, times(1)).savePhoto(loggedUser, newPhoto);
        verify(cloudDatabase, times(1)).savePhoto(loggedUser, newPhoto);
    }

    @Test
    public void testGetUserPhotos()  {
        interactor.getUserPhotos();

        verify(authenticator, times(1)).getLoggedUser();
        verify(localDatabase, times(1)).getPhotosByUser(loggedUser);
    }

}
