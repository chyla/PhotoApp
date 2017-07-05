package org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail.objects;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.net.MalformedURLException;
import java.net.URL;

@Root(name = "photo", strict = false)
public class FlickrPhoto {

    @Attribute(name = "id")
    Long id;

    @Attribute(name = "title")
    String title;

    @Element(name = "description", required = false)
    String description;

    @Attribute(name = "secret")
    String secret;

    @Attribute(name = "server")
    Integer server;

    @Attribute(name = "farm")
    Integer farm;

    public Long getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public Integer getServer() {
        return server;
    }

    public Integer getFarm() {
        return farm;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public URL getUrl() throws MalformedURLException {
        String pattern = "https://farm%d.staticflickr.com/%d/%d_%s.jpg";
        String url = String.format(pattern, farm, server, id, secret);
        return new URL(url);
    }

}
