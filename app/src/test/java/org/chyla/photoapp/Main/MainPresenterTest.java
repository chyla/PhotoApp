package org.chyla.photoapp.Main;

import org.chyla.photoapp.BaseTest;
import org.chyla.photoapp.BuildConfig;
import org.chyla.photoapp.Main.Model.InspectPhotosInteractor;
import org.chyla.photoapp.Main.Model.LastPhotoInteractor;
import org.chyla.photoapp.Main.Model.NewPhotoInteractor;
import org.chyla.photoapp.Main.Model.UserGalleryInteractor;
import org.chyla.photoapp.Main.Model.detail.Event.ShowInspectedPhotosEvent;
import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.Main.Presenter.MainPresenterImpl;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainPresenterTest extends BaseTest {

    @Mock
    MainView view;

    @Mock
    Authenticator authenticator;

    @Mock
    LastPhotoInteractor lastPhotoInteractor;

    @Mock
    NewPhotoInteractor newPhotoInteractor;

    @Mock
    InspectPhotosInteractor inspectPhotosInteractor;

    @Mock
    UserGalleryInteractor userGalleryInteractor;

    MainPresenter presenter;

    @Override
    public void setUp() {
        super.setUp();

        presenter = new MainPresenterImpl(
                view,
                authenticator,
                lastPhotoInteractor,
                newPhotoInteractor,
                inspectPhotosInteractor,
                userGalleryInteractor);
    }

    @After
    public void tearDown() {
        verify(newPhotoInteractor, times(1)).setPresenter(presenter);

        verifyNoMoreInteractions(view);
        verifyNoMoreInteractions(authenticator);
        verifyNoMoreInteractions(lastPhotoInteractor);
        verifyNoMoreInteractions(newPhotoInteractor);
        verifyNoMoreInteractions(inspectPhotosInteractor);
        verifyNoMoreInteractions(userGalleryInteractor);
    }

    @Test
    public void testPresenterStartupAndShutdown() {
        // empty body
    }

    @Test
    public void testLogout() {
        presenter.logoutUser();

        verify(authenticator, times(1)).logoutUser();
        verify(view, times(1)).startLoginActivity();
    }

    @Test
    public void testShowLastPhoto() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));
        when(lastPhotoInteractor.getLastPhoto()).thenAnswer(new Answer<Photo>() {
            @Override
            public Photo answer(InvocationOnMock invocationOnMock) throws Throwable {
                return examplePhoto;
            }
        });

        presenter.showLastPhoto();

        verify(lastPhotoInteractor, times(1)).getLastPhoto();
        verify(view, times(1)).showPhoto(examplePhoto);
    }

    @Test
    public void testAddNewPhoto() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));

        presenter.addNewPhoto(examplePhoto.getTitle(), examplePhoto.getDescription(), examplePhoto.getUrl().toString());

        verify(newPhotoInteractor, times(1)).addNewPhoto(examplePhoto.getTitle(), examplePhoto.getDescription(), examplePhoto.getUrl().toString());
    }

    @Test
    public void testShowPhoto() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));

        presenter.showPhoto(examplePhoto);

        verify(view, times(1)).showPhoto(examplePhoto);
    }


    @Test
    public void testInspectPhotos() {
        final String tags = "car,dog";
        final List<String> tagsList = new ArrayList<>();
        tagsList.add("car");
        tagsList.add("dog");

        presenter.inspectPhotos(tags);

        verify(inspectPhotosInteractor, times(1)).inspectPhotos(tagsList);
    }

    @Test
    public void testShowInspectedPhoto() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));

        presenter.showInspectedPhoto(examplePhoto);

        verify(view, times(1)).showInspectedPhoto(examplePhoto);
    }

    @Test
    public void testOnStart() {
        final User exampleUser = new User("USER_ID1", "mail@example.com");
        when(authenticator.getLoggedUser()).thenAnswer(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return exampleUser;
            }
        });

        presenter.onStart();

        verify(authenticator, times(1)).getLoggedUser();
        verify(view, times(1)).setUserMail(exampleUser.getEmail());
    }

    @Test
    public void testOnShowInspectedPhotosEvent() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));
        final Photo examplePhoto2 = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));
        final List<Photo> photos = new ArrayList<>();
        photos.add(examplePhoto);
        photos.add(examplePhoto2);
        final ShowInspectedPhotosEvent event = new ShowInspectedPhotosEvent(photos);

        presenter.onShowInspectedPhotosEvent(event);

        verify(view, times(1)).showInspectedPhotosGallery(photos);
    }

    @Test
    public void testAddPhotoToGallery() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));
        final Photo examplePhoto2 = new Photo("TITLE2", "DESCRIPTION2", new URL("http://example2.com/"));
        final List<Photo> photosGallery = new ArrayList<>();
        photosGallery.add(examplePhoto);
        photosGallery.add(examplePhoto2);
        when(userGalleryInteractor.getUserPhotos()).thenAnswer(new Answer<List<Photo>>() {
            @Override
            public List<Photo> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return photosGallery;
            }
        });

        presenter.addPhotoToGallery(examplePhoto);

        verify(userGalleryInteractor, times(1)).addPhotoToGallery(examplePhoto);
        verify(userGalleryInteractor, times(1)).getUserPhotos();
        verify(view, times(1)).showUserGallery(photosGallery);
    }

    @Test
    public void testShowUserGallery() throws MalformedURLException {
        final Photo examplePhoto = new Photo("TITLE1", "DESCRIPTION1", new URL("http://example.com/"));
        final Photo examplePhoto2 = new Photo("TITLE2", "DESCRIPTION2", new URL("http://example2.com/"));
        final List<Photo> photosGallery = new ArrayList<>();
        photosGallery.add(examplePhoto);
        photosGallery.add(examplePhoto2);
        when(userGalleryInteractor.getUserPhotos()).thenAnswer(new Answer<List<Photo>>() {
            @Override
            public List<Photo> answer(InvocationOnMock invocationOnMock) throws Throwable {
                return photosGallery;
            }
        });

        presenter.showUserGallery();

        verify(userGalleryInteractor, times(1)).getUserPhotos();
        verify(view, times(1)).showUserGallery(photosGallery);
    }

}
