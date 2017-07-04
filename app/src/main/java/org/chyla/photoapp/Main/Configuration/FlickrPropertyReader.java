package org.chyla.photoapp.Main.Configuration;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FlickrPropertyReader {

    private final static String PROPERTY_FILE_NAME = "flickr.properties";
    private final Properties properties;

    public FlickrPropertyReader(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(PROPERTY_FILE_NAME);

        properties = new Properties();
        properties.load(inputStream);
    }

    public String getApiKey() {
        return properties.getProperty("API_KEY");
    }

}
