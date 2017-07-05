package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Model.detail.InspectPhotosCallback;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.CloudPhotosExplorerRepository;

import java.util.List;

public class InspectPhotosInteractorImpl implements InspectPhotosInteractor {

    private final CloudPhotosExplorerRepository repository;

    public InspectPhotosInteractorImpl(CloudPhotosExplorerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void inspectPhotos(List<String> tags) {
        InspectPhotosCallback callback = new InspectPhotosCallback();

        repository.getPhotosByTags(tags, callback);
    }

}
