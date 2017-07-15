package org.chyla.photoapp.Synchronization;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.LastPhotoCallback;
import org.chyla.photoapp.Repository.CloudDatabase.PhotoGalleryCallback;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Synchronization.Model.SynchronizeDataInteractor;
import org.chyla.photoapp.Synchronization.Model.SynchronizeDataInteractorImpl;
import org.chyla.photoapp.Synchronization.Model.detail.SynchronizationFinishedEvent;
import org.greenrobot.eventbus.EventBus;
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
import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class SynchronizeDataInteractorTest extends BaseTest {

    @Mock
    CloudDatabaseRepository cloudDatabaseRepository;

    @Mock
    DatabaseRepository databaseRepository;

    @Mock
    Authenticator authenticator;

    @Mock
    EventBus bus;

    User loggedUser;

    SynchronizeDataInteractor interactor;

    @Override
    public void setUp() {
        super.setUp();

        when(authenticator.getLoggedUser()).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return loggedUser;
            }
        });

        interactor = new SynchronizeDataInteractorImpl(cloudDatabaseRepository, databaseRepository, authenticator) {
            @Override
            protected EventBus getEventBus() {
                return bus;
            }
        };
    }

    @After
    public void tearDown() {
        verify(authenticator, times(1)).getLoggedUser();

        verifyNoMoreInteractions(cloudDatabaseRepository);
        verifyNoMoreInteractions(databaseRepository);
        verifyNoMoreInteractions(authenticator);
        verifyNoMoreInteractions(bus);
    }

    @Test
    public void testConstructor() {
        // empty body
    }

    @Test
    public void testSynchronizeData() throws MalformedURLException {
        final Photo lastPhoto = new Photo("TITLE1", "DESC1", new URL("http://example1.com"));
        final Photo galleryPhoto = new Photo("TITLE2", "DESC2", new URL("http://example2.com"));

        final List<Photo> gallery = new LinkedList<>();
        gallery.add(lastPhoto);
        gallery.add(galleryPhoto);

        final ArgumentCaptor<User> galleryUserCaptor = ArgumentCaptor.forClass(User.class);
        final ArgumentCaptor<PhotoGalleryCallback> galleryCallback = ArgumentCaptor.forClass(PhotoGalleryCallback.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                galleryCallback.getValue().onSuccess(gallery);
                return null;
            }
        }).when(cloudDatabaseRepository).getPhotoGallery(galleryUserCaptor.capture(), galleryCallback.capture());

        final ArgumentCaptor<User> lastUserCaptor = ArgumentCaptor.forClass(User.class);
        final ArgumentCaptor<LastPhotoCallback> lastPhotoCallback = ArgumentCaptor.forClass(LastPhotoCallback.class);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                lastPhotoCallback.getValue().onSuccess(lastPhoto);
                return null;
            }
        }).when(cloudDatabaseRepository).getLastPhoto(lastUserCaptor.capture(), lastPhotoCallback.capture());


        interactor.synchronizeData();


        verify(cloudDatabaseRepository, times(1)).getPhotoGallery(loggedUser, galleryCallback.getValue());
        verify(databaseRepository, times(1)).savePhoto(loggedUser, lastPhoto);
        verify(databaseRepository, times(1)).savePhoto(loggedUser, galleryPhoto);

        verify(cloudDatabaseRepository, times(1)).getLastPhoto(loggedUser, lastPhotoCallback.getValue());
        verify(databaseRepository, times(1)).saveLastPhoto(loggedUser, lastPhoto);

        final ArgumentCaptor<SynchronizationFinishedEvent> event = ArgumentCaptor.forClass(SynchronizationFinishedEvent.class);
        verify(bus, times(1)).post(event.capture());
    }

}
