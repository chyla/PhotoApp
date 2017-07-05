package org.chyla.photoapp.Main.Repository.CloudPhotosExplorer;

import java.util.List;

public interface CloudPhotosExplorerRepository {

    void getPhotosByTags(final List<String> tags, GetPhotosCallback callback);

}
