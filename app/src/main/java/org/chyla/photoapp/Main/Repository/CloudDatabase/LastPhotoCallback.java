package org.chyla.photoapp.Main.Repository.CloudDatabase;

import org.chyla.photoapp.Main.Model.objects.Photo;

public interface LastPhotoCallback {

    void onSuccess(final Photo photo);

    void onFailure();

}
