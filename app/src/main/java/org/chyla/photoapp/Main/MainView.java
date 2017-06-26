package org.chyla.photoapp.Main;

import org.chyla.photoapp.Main.Model.Photo;

import java.util.List;

public interface MainView {

    void startLoginActivity();

    void showInspectedPhotosGallery(final List<Photo> photos);

}
