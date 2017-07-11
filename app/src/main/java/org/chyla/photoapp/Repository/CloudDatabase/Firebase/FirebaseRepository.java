package org.chyla.photoapp.Repository.CloudDatabase.Firebase;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.chyla.photoapp.Main.Model.objects.Photo;
import org.chyla.photoapp.Main.Model.objects.User;
import org.chyla.photoapp.Repository.CloudDatabase.CloudDatabaseRepository;
import org.chyla.photoapp.Repository.CloudDatabase.Firebase.detail.DbPhoto;
import org.chyla.photoapp.Repository.CloudDatabase.LastPhotoCallback;
import org.chyla.photoapp.Repository.CloudDatabase.PhotoGalleryCallback;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class FirebaseRepository implements CloudDatabaseRepository {

    private final static String  LOG_TAG = "FirebaseRepository";
    private final DatabaseReference databaseReference;

    public FirebaseRepository() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void saveLastPhoto(final User user, final Photo photo) {
        Log.i(LOG_TAG, "Saving last photo in cloud database...");

        DbPhoto dbPhoto = new DbPhoto(photo.getTitle(), photo.getDescription(), photo.getUrl().toString());
        new AsyncSaveLastPhoto().execute(new SavePhotoHelper(user.getGoogleUserId(), dbPhoto));
    }

    @Override
    public void getLastPhoto(final User user, final LastPhotoCallback callback) {
        Log.i(LOG_TAG, "Looking for last photo in cloud database...");

        final ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final DataSnapshot photoSnapshot = dataSnapshot.child(user.getGoogleUserId()).child("lastPhoto");

                Photo photo = null;

                if (photoSnapshot != null) {
                    try {
                        Log.i(LOG_TAG, "Found last photo in cloud database.");

                        photo = toPhoto(photoSnapshot);
                    } catch (MalformedURLException e) {
                        Log.e(LOG_TAG, "Found photo with broken url, ignoring...");
                        e.printStackTrace();
                    }
                }
                else {
                    Log.i(LOG_TAG, "Last photo not found in cloud database.");
                }

                callback.onSuccess(photo);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.e(LOG_TAG, "Failed to get gallery from Firebase. Cause: " + databaseError.getMessage());
                callback.onFailure();
            }
        };

        databaseReference.addListenerForSingleValueEvent(listener);
    }

    @Override
    public void savePhoto(final User user, final Photo photo) {
        DbPhoto dbPhoto = new DbPhoto(photo.getTitle(), photo.getDescription(), photo.getUrl().toString());
        new AsyncSavePhoto().execute(new SavePhotoHelper(user.getGoogleUserId(), dbPhoto));
    }

    @Override
    public void getPhotoGallery(final User user, final PhotoGalleryCallback callback) {
        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final Iterable<DataSnapshot> photosSnapshot = dataSnapshot.child(user.getGoogleUserId()).child("gallery").getChildren();

                final List<Photo> photos = toPhotosList(photosSnapshot);

                callback.onSuccess(photos);
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.e(LOG_TAG, "Failed to get gallery from Firebase. Cause: " + databaseError.getMessage());
                callback.onFailure();
            }
        };

        databaseReference.addListenerForSingleValueEvent(listener);
    }

    private class SavePhotoHelper {
        final String userId;
        final DbPhoto photo;

        public SavePhotoHelper(final String userId, final DbPhoto photo) {
            this.userId = userId;
            this.photo = photo;
        }
    }

    private class AsyncSavePhoto extends AsyncTask<SavePhotoHelper, Void, Void> {
        @Override
        protected Void doInBackground(SavePhotoHelper... params) {
            final SavePhotoHelper helper = params[0];

            final DatabaseReference element = databaseReference.child(helper.userId).child("gallery").push();
            element.setValue(helper.photo);

            return null;
        }
    }

    private class AsyncSaveLastPhoto extends AsyncTask<SavePhotoHelper, Void, Void> {
        @Override
        protected Void doInBackground(SavePhotoHelper... params) {
            final SavePhotoHelper helper = params[0];

            final DatabaseReference element = databaseReference.child(helper.userId).child("lastPhoto");
            element.setValue(helper.photo);

            return null;
        }
    }

    private List<Photo> toPhotosList(final Iterable<DataSnapshot> photosSnapshot) {
        final List<Photo> photos = new LinkedList<>();

        for (final DataSnapshot snapshot : photosSnapshot) {
            try {
                final Photo photo = toPhoto(snapshot);
                photos.add(photo);
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Failed to parse photo url, ignoring...");
                e.printStackTrace();
            }
        }

        return photos;
    }

    private Photo toPhoto(final DataSnapshot photoSnapshot) throws MalformedURLException {
        HashMap dbPhoto = (HashMap) photoSnapshot.getValue();

        Photo photo = null;

        if (photo != null) {
            photo = new Photo(
                    dbPhoto.get("title").toString(),
                    dbPhoto.get("description").toString(),
                    new URL(dbPhoto.get("url").toString())
            );
        }

        return photo;
    }

}
