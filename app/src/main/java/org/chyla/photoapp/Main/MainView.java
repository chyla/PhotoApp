package org.chyla.photoapp.Main;

import org.chyla.photoapp.Main.Model.objects.Photo;

import java.util.List;

public interface MainView {

    void startLoginActivity();

    void showPhoto(final Photo photo);
    void showUserGallery(final List<Photo> photos);
    void showInspectedPhotosGallery(final List<Photo> photos);
    void showInspectedPhoto(final Photo photo);

    void setUserMail(final String mail);

}
