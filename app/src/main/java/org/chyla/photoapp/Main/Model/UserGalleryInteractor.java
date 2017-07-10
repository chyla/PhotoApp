package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Model.objects.Photo;

import java.util.List;

public interface UserGalleryInteractor {

    void addPhotoToGallery(final Photo photo);

    List<Photo> getUserPhotos();

}
