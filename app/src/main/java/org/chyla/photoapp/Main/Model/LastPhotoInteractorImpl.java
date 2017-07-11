package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Model.Authenticator.Authenticator;

public class LastPhotoInteractorImpl implements LastPhotoInteractor {

    Authenticator authenticator;
    DatabaseRepository databaseRepository;

    public LastPhotoInteractorImpl(Authenticator authenticator, DatabaseRepository databaseRepository) {
        this.authenticator = authenticator;
        this.databaseRepository = databaseRepository;
    }

    @Override
    public Photo getLastPhoto() {
        final User currentUser = authenticator.getLoggedUser();

        return databaseRepository.getLastPhoto(currentUser);
    }

}
