package org.chyla.photoapp.Main;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Main.Model.NewPhotoInteractor;
import org.chyla.photoapp.Main.Model.NewPhotoInteractorImpl;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.CloudStorageRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.UploadImageCallback;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class NewPhotoInteractorTest extends BaseTest {

    @Mock
    CloudStorageRepository cloudStorage;

    @Mock
    DatabaseRepository localDatabase;

    @Mock
    CloudDatabaseRepository cloudDatabase;

    @Mock
    Authenticator authenticator;

    @Mock
    MainPresenter presenter;

    final User loggedUser = new User("USER_ID1", "user@example.com");

    NewPhotoInteractor interactor;

    @Override
    public void setUp() {
        super.setUp();

        interactor = new NewPhotoInteractorImpl(cloudStorage, cloudDatabase, localDatabase, authenticator);
        interactor.setPresenter(presenter);

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

        when(authenticator.getLoggedUser()).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return loggedUser;
            }
        });

        final ArgumentCaptor<String> path = ArgumentCaptor.forClass(String.class);
        final ArgumentCaptor<UploadImageCallback> callback = ArgumentCaptor.forClass(UploadImageCallback.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                callback.getValue().onSuccess(newPhoto.getUrl());
                return null;
            }
        }).when(cloudStorage).uploadPhoto(path.capture(), callback.capture());


        interactor.addNewPhoto(newPhoto.getTitle(), newPhoto.getDescription(), "/example/path");


        verify(authenticator, times(1)).getLoggedUser();

        final ArgumentCaptor<User> localUser = ArgumentCaptor.forClass(User.class);
        final ArgumentCaptor<Photo> localPhoto = ArgumentCaptor.forClass(Photo.class);
        verify(localDatabase, times(1)).savePhoto(localUser.capture(), localPhoto.capture());
        check(localUser.getValue(), newPhoto, localPhoto.getValue());

        final ArgumentCaptor<User> cloudUser = ArgumentCaptor.forClass(User.class);
        final ArgumentCaptor<Photo> cloudPhoto = ArgumentCaptor.forClass(Photo.class);
        verify(cloudDatabase, times(1)).savePhoto(cloudUser.capture(), cloudPhoto.capture());
        check(cloudUser.getValue(), newPhoto, cloudPhoto.getValue());

        final ArgumentCaptor<User> localLastUser = ArgumentCaptor.forClass(User.class);
        final ArgumentCaptor<Photo> localLastPhoto = ArgumentCaptor.forClass(Photo.class);
        verify(localDatabase, times(1)).saveLastPhoto(localLastUser.capture(), localLastPhoto.capture());
        check(localLastUser.getValue(), newPhoto, localLastPhoto.getValue());

        final ArgumentCaptor<User> cloudLastUser = ArgumentCaptor.forClass(User.class);
        final ArgumentCaptor<Photo> cloudLastPhoto = ArgumentCaptor.forClass(Photo.class);
        verify(cloudDatabase, times(1)).saveLastPhoto(cloudLastUser.capture(), cloudLastPhoto.capture());
        check(cloudLastUser.getValue(), newPhoto, cloudLastPhoto.getValue());
    }

    private void check(final User user, final Photo expectedPhoto, final Photo receivedPhoto) {
        assertEquals(loggedUser, user);

        assertEquals(expectedPhoto.getTitle(), receivedPhoto.getTitle());
        assertEquals(expectedPhoto.getDescription(), receivedPhoto.getDescription());
        assertEquals(expectedPhoto.getUrl().toString(), receivedPhoto.getUrl().toString());
    }

}
