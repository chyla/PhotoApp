package org.chyla.photoapp.Main.Model;

import android.support.annotation.Nullable;

import java.net.URL;

public class Photo {

    private final String title;
    private final String description;
    private final URL url;

    public Photo(String title, @Nullable String description, URL url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        if (description == null) {
            return "";
        }
        else {
            return description;
        }
    }

    public URL getUrl() {
        return url;
    }

}
