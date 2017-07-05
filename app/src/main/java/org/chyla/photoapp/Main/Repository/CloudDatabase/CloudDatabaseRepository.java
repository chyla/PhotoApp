package org.chyla.photoapp.Main.Repository.CloudDatabase;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;

public interface CloudDatabaseRepository {

    void savePhoto(final User user, final Photo photo);

    void getPhotoGallery(final User user, final PhotoGalleryCallback callback);

}