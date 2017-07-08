package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Main.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Model.Authenticator.Authenticator;

import java.util.List;

public class UserGalleryInteractorImpl implements UserGalleryInteractor {

    private final DatabaseRepository database;
    private final Authenticator authenticator;

    public UserGalleryInteractorImpl(final DatabaseRepository database, final Authenticator authenticator) {
        this.database = database;
        this.authenticator = authenticator;
    }

    @Override
    public List<Photo> getUserPhotos() {
        final User currentUser = authenticator.getLoggedUser();

        return database.getPhotosByUser(currentUser);
    }

}
