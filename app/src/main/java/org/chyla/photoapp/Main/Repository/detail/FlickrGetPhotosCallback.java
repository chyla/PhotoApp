package org.chyla.photoapp.Main.Repository.detail;

import android.util.Log;

import org.chyla.photoapp.Main.Model.Photo;
import org.chyla.photoapp.Main.Repository.InspectPhotosCallback;
import org.chyla.photoapp.Main.Repository.detail.objects.FlickrPhoto;
import org.chyla.photoapp.Main.Repository.detail.objects.FlickrResponse;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlickrGetPhotosCallback implements Callback<FlickrResponse> {

    private static final String LOG_TAG = "FlickrGetPhotosCallback";
    private InspectPhotosCallback callback;

    public FlickrGetPhotosCallback(InspectPhotosCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
         if (response.isSuccessful()) {
             Log.i(LOG_TAG, "Received successful response.");

             FlickrResponse flickrResponse = response.body();
             List<FlickrPhoto> flickrPhotos = flickrResponse.getPhotos();

             List<Photo> appPhotos = new ArrayList<>(flickrPhotos.size());

             for (FlickrPhoto flickrPhoto : flickrPhotos) {
                 try {
                     Photo photo =  new Photo(flickrPhoto.getTitle(),
                                              flickrPhoto.getDescription(),
                                              flickrPhoto.getUrl());
                     appPhotos.add(photo);
                 } catch (MalformedURLException e) {
                     Log.e(LOG_TAG, "Error while creating app photo URL...");
                     e.printStackTrace();
                 }
             }

             callback.inspectPhotosCallback(appPhotos);
         }
         else {
             Log.e(LOG_TAG, "Received error response:");
             Log.e(LOG_TAG, response.errorBody().toString());
         }
    }

    @Override
    public void onFailure(Call<FlickrResponse> call, Throwable t) {
        Log.e(LOG_TAG, "Failure:");
        t.printStackTrace();
    }

}
