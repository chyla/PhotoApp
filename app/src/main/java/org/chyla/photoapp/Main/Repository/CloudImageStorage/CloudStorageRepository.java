package org.chyla.photoapp.Main.Repository.CloudImageStorage;

public interface CloudStorageRepository {

    void uploadPhoto(final String path, final UploadImageCallback callback);

}
