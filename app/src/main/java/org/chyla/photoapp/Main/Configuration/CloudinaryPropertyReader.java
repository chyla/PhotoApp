package org.chyla.photoapp.Main.Configuration;

import android.content.Context;
import android.content.res.AssetManager;

import org.chyla.photoapp.Main.Configuration.detail.CloudinaryConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CloudinaryPropertyReader {

    private final static String PROPERTY_FILE_NAME = "cloudinary.properties";
    private CloudinaryConfig config;

    public CloudinaryPropertyReader(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(PROPERTY_FILE_NAME);

        final Properties properties = new Properties();
        properties.load(inputStream);

        config = new CloudinaryConfig(
                properties.getProperty("CLOUD_NAME"),
                properties.getProperty("API_KEY"),
                properties.getProperty("API_SECRET")
        );
    }

    public CloudinaryConfig getConfig() {
        return config;
    }

}
