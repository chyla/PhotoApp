package org.chyla.photoapp.Main.Model;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Model.Authenticator.Authenticator;

import java.util.List;

public class UserGalleryInteractorImpl implements UserGalleryInteractor {

    private final Authenticator authenticator;
    private final CloudDatabaseRepository cloudDatabase;
    private final DatabaseRepository database;

    public UserGalleryInteractorImpl(final Authenticator authenticator, final CloudDatabaseRepository cloudDatabase, final DatabaseRepository database) {
        this.database = database;
        this.cloudDatabase = cloudDatabase;
        this.authenticator = authenticator;
    }

    @Override
    public void addPhotoToGallery(final Photo photo) {
        final User currentUser = authenticator.getLoggedUser();

        cloudDatabase.savePhoto(currentUser, photo);
        database.savePhoto(currentUser, photo);
    }

    @Override
    public List<Photo> getUserPhotos() {
        final User currentUser = authenticator.getLoggedUser();

        return database.getPhotosByUser(currentUser);
    }

}
