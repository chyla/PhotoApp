package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Model.detail.InspectPhotosCallback;
import org.chyla.photoapp.Main.Repository.FlickrRepository;
import org.chyla.photoapp.Main.Repository.InspectPhotosRepository;

import java.util.List;

public class InspectPhotosInteractorImpl implements InspectPhotosInteractor {

    private InspectPhotosRepository inspectPhotosRepository;

    public InspectPhotosInteractorImpl() {
        inspectPhotosRepository = new FlickrRepository();
    }

    @Override
    public void inspectPhotos(List<String> tags) {
        InspectPhotosCallback callback = new InspectPhotosCallback();

        inspectPhotosRepository.getPhotosByTags(tags, callback);
    }

}
