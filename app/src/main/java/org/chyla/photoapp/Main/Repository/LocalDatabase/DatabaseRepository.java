package org.chyla.photoapp.Main.Repository.LocalDatabase;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;

import java.util.List;

public interface DatabaseRepository {

    void savePhoto(final User user, final Photo photo);

    List<Photo> getPhotosByUser(final User user);

}
