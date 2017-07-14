package org.chyla.photoapp.Main.Repository.CloudImageStorage.Cloudinary;

import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.Cloudinary;

import org.chyla.photoapp.Main.Configuration.detail.CloudinaryConfig;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.CloudStorageRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.UploadImageCallback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryRepository implements CloudStorageRepository {

    private final static String LOG_TAG = "CloudinaryRepository";
    private final Cloudinary cloudinary;

    public CloudinaryRepository(CloudinaryConfig config) {
        cloudinary = getCloudinary(config);
    }

    protected Cloudinary getCloudinary(CloudinaryConfig config) {
        Map cfg = new HashMap();
        cfg.put("cloud_name", config.getCloudName());
        cfg.put("api_key", config.getApiKey());
        cfg.put("api_secret", config.getApiSecret());

        return new Cloudinary(cfg);
    }

    @Override
    public void uploadPhoto(final String path, final UploadImageCallback callback) {
        Log.i(LOG_TAG, "Uploading photo to Cloudinary...");

        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                try {
                    Map result = cloudinary.uploader().upload(path, Cloudinary.asMap());
                    URL url;

                    if (result.containsKey("secure_url")) {
                        url = new URL((String) result.get("secure_url"));
                    } else if (result.containsKey("url")) {
                        url = new URL((String) result.get("url"));
                    } else {
                        final String errorMessage = "Unable to find image url.";
                        Log.e(LOG_TAG, errorMessage);
                        callback.onFailure(errorMessage);
                        return null;
                    }

                    Log.i(LOG_TAG, "Image uploaded successfully.");
                    callback.onSuccess(url);

                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Failed to parse image url, cause: " + e.getMessage());
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Failed to upload image, cause: " + e.getMessage());
                    e.printStackTrace();
                    callback.onFailure(e.getMessage());
                }

                return null;
            }
        }.execute();
    }

}
