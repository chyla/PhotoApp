package org.chyla.photoapp.Main;

import org.chyla.photoapp.Main.Model.objects.Photo;

import java.util.List;

public interface MainView {

    void startLoginActivity();

    void showLastPhoto(final Photo photo);
    void showInspectedPhotosGallery(final List<Photo> photos);
    void showInspectedPhoto(final Photo photo);

}
