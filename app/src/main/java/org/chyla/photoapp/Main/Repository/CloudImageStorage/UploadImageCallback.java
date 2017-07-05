package org.chyla.photoapp.Main.Repository.CloudImageStorage;

import java.net.URL;

public interface UploadImageCallback {

    void onSuccess(final URL url);

    void onFailure(final String errorMessage);

}
