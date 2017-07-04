package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Model.detail.InspectPhotosCallback;
import org.chyla.photoapp.Main.Repository.FlickrRepository;
import org.chyla.photoapp.Main.Repository.InspectPhotosRepository;

import java.util.List;

public class InspectPhotosInteractorImpl implements InspectPhotosInteractor {

    private final InspectPhotosRepository inspectPhotosRepository;

    public InspectPhotosInteractorImpl(InspectPhotosRepository repository) {
        inspectPhotosRepository = repository;
    }

    @Override
    public void inspectPhotos(List<String> tags) {
        InspectPhotosCallback callback = new InspectPhotosCallback();

        inspectPhotosRepository.getPhotosByTags(tags, callback);
    }

}
