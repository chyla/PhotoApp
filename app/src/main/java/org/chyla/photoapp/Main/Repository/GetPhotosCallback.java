package org.chyla.photoapp.Main.Repository;

import org.chyla.photoapp.Main.Model.Photo;

import java.util.List;

public interface GetPhotosCallback {

    void getPhotosCallback(final List<Photo> photos);

}
