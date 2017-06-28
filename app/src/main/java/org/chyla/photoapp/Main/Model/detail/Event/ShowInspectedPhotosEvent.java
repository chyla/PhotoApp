package org.chyla.photoapp.Main.Model.detail.Event;

import org.chyla.photoapp.Main.Model.objects.Photo;

import java.util.List;

public class ShowInspectedPhotosEvent {

    private final List<Photo> photos;

    public ShowInspectedPhotosEvent(final List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

}
