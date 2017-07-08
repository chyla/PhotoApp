package org.chyla.photoapp.Main.Presenter;

import org.chyla.photoapp.Main.Model.detail.Event.ShowInspectedPhotosEvent;
import org.chyla.photoapp.Main.Model.objects.Photo;

public interface MainPresenter {

    void onStart();
    void onStop();

    void logoutUser();

    void showPhoto(final Photo photo);

    void inspectPhotos(final String tags);
    void showInspectedPhoto(final Photo photo);
    void onShowInspectedPhotosEvent(final ShowInspectedPhotosEvent event);

    void showUserGallery();
}
