package org.chyla.photoapp.Main.Repository;

import org.chyla.photoapp.Main.Model.objects.Photo;

import java.util.List;

public interface GetPhotosCallback {

    void getPhotosCallback(final List<Photo> photos);

}
