package org.chyla.photoapp.Main.Repository;

import java.util.List;

public interface InspectPhotosRepository {

    void getPhotosByTags(final List<String> tags, GetPhotosCallback callback);

}
