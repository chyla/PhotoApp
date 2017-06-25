package org.chyla.photoapp.Main.Repository;

import java.util.List;

public interface InspectPhotosRepository {

    void getPhotosByTags(List<String> tags, InspectPhotosCallback callback);

}
