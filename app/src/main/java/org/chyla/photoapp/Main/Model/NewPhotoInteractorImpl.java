package org.chyla.photoapp.Main.Model;

import android.util.Log;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Main.Presenter.MainPresenter;
import org.chyla.photoapp.Main.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.CloudStorageRepository;
import org.chyla.photoapp.Main.Repository.CloudImageStorage.UploadImageCallback;
import org.chyla.photoapp.Main.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Model.Authenticator.Authenticator;

import java.net.URL;

public class NewPhotoInteractorImpl implements NewPhotoInteractor {

    private final static String LOG_TAG = "NewPhotoInteractorImpl";

    MainPresenter presenter;

    CloudStorageRepository cloudStorage;
    CloudDatabaseRepository cloudDatabase;
    DatabaseRepository localDatabase;
    Authenticator authenticator;

    public NewPhotoInteractorImpl(CloudStorageRepository cloudStorage, CloudDatabaseRepository cloudDatabase, DatabaseRepository localDatabase, Authenticator authenticator) {
        this.cloudStorage = cloudStorage;
        this.cloudDatabase = cloudDatabase;
        this.localDatabase = localDatabase;
        this.authenticator = authenticator;
    }

    @Override
    public void setPresenter(MainPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void addNewPhoto(final String title, final String description, final String path) {
        cloudStorage.uploadPhoto(path, new UploadImageCallback() {
            @Override
            public void onSuccess(final URL url) {
                Log.i(LOG_TAG, "New photo uploaded.");

                final Photo photo = new Photo(title, description, url);

                addToDatabases(photo);

                presenter.showPhoto(photo);
            }

            @Override
            public void onFailure(final String errorMessage) {
                Log.i(LOG_TAG, "Failed to upload photo, cause: " + errorMessage.toString());
            }
        });
    }

    private void addToDatabases(final Photo photo) {
        final User currentUser = authenticator.getLoggedUser();

        cloudDatabase.savePhoto(currentUser, photo);
        localDatabase.savePhoto(currentUser, photo);

        localDatabase.saveLastPhoto(currentUser, photo);
    }

}
