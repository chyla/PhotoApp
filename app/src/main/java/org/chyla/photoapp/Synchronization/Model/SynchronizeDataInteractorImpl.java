package org.chyla.photoapp.Synchronization.Model;

import android.util.Log;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Model.Authenticator.Authenticator;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.LastPhotoCallback;
import org.chyla.photoapp.Repository.CloudDatabase.PhotoGalleryCallback;
import org.chyla.photoapp.Repository.LocalDatabase.DatabaseRepository;
import org.chyla.photoapp.Synchronization.Model.detail.SynchronizationFinishedEvent;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SynchronizeDataInteractorImpl implements SynchronizeDataInteractor {

    private final static String LOG_TAG = "SynchronizeDataInteract";

    private final CloudDatabaseRepository cloudDatabase;
    private final DatabaseRepository localDatabase;

    private final User currentUser;
    private EventBus eventBus;

    public SynchronizeDataInteractorImpl(final CloudDatabaseRepository cloudDatabase, final DatabaseRepository localDatabase, final Authenticator authenticator) {
        this.cloudDatabase = cloudDatabase;
        this.localDatabase = localDatabase;

        currentUser = authenticator.getLoggedUser();

        eventBus = getEventBus();
    }

    protected EventBus getEventBus() {
        return EventBus.getDefault();
    }

    @Override
    public void synchronizeData() {
        Log.i(LOG_TAG, "Synchronizing data...");

        synchronizePhotoGallery();
    }

    private void synchronizePhotoGallery() {
        Log.i(LOG_TAG, "Loading photo gallery from cloud database...");
        cloudDatabase.getPhotoGallery(currentUser, new PhotoGalleryCallback() {
            @Override
            public void onSuccess(final List<Photo> photos) {
                Log.i(LOG_TAG, "Photo gallery loaded from cloud database... Found: " + photos.size() + " items.");

                Log.i(LOG_TAG, "Saving photo gallery to local database...");
                for (final Photo photo : photos) {
                    localDatabase.savePhoto(currentUser, photo);
                }
                Log.i(LOG_TAG, "Photo gallery saved...");

                synchronizeLastPhoto();
            }

            @Override
            public void onFailure() {
                Log.e(LOG_TAG, "Failed to load photo gallery...");
            }
        });
    }

    private void synchronizeLastPhoto() {
        Log.i(LOG_TAG, "Loading last photo from cloud database...");

        cloudDatabase.getLastPhoto(currentUser, new LastPhotoCallback() {
            @Override
            public void onSuccess(final Photo photo) {
                Log.i(LOG_TAG, "Last photo loaded from cloud database...");

                if (photo != null) {
                    Log.i(LOG_TAG, "Saving last photo to local database...");
                    localDatabase.saveLastPhoto(currentUser, photo);
                    Log.i(LOG_TAG, "Last photo saved...");
                }
                else {
                    Log.i(LOG_TAG, "Last photo not found, ok.");
                }

                finish();
            }

            @Override
            public void onFailure() {
                Log.e(LOG_TAG, "Failed to load last photo...");
            }
        });

    }

    private void finish() {
        Log.i(LOG_TAG, "Finished...");
        eventBus.post(new SynchronizationFinishedEvent());
    }

}
