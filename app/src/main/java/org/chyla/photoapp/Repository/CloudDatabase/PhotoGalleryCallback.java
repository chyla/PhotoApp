package org.chyla.photoapp.Repository.CloudDatabase;

import org.chyla.photoapp.Main.Model.objects.Photo;

import java.util.List;

public interface PhotoGalleryCallback {

    void onSuccess(final List<Photo> photos);

    void onFailure();

}
