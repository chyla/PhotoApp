package org.chyla.photoapp.Main.Repository.CloudDatabase;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;

public interface CloudDatabaseRepository {

    void saveLastPhoto(final User user, final Photo photo);
    void getLastPhoto(final User user, final LastPhotoCallback callback);

    void savePhoto(final User user, final Photo photo);

    void getPhotoGallery(final User user, final PhotoGalleryCallback callback);

}
