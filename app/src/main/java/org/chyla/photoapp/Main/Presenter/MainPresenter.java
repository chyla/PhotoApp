package org.chyla.photoapp.Main.Presenter;

import org.chyla.photoapp.Main.Model.detail.Event.ShowInspectedPhotosEvent;

public interface MainPresenter {

    void onStart();
    void onStop();

    void logoutUser();

    void inspectPhotos(final String tags);
    void onShowInspectedPhotosEvent(final ShowInspectedPhotosEvent event);

}
