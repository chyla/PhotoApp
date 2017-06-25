package org.chyla.photoapp.Main.Model;

import java.net.URL;

public class Photo {

    String title;
    String description;
    URL url;

    public Photo(String title, String description, URL url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public URL getUrl() {
        return url;
    }

}
