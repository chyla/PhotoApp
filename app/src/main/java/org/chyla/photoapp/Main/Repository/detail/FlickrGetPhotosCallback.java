package org.chyla.photoapp.Main.Repository.detail;

import android.util.Log;

import org.chyla.photoapp.Main.Model.Photo;
import org.chyla.photoapp.Main.Repository.InspectPhotosCallback;
import org.chyla.photoapp.Main.Repository.detail.objects.FlickrPhoto;
import org.chyla.photoapp.Main.Repository.detail.objects.SearchResponse;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlickrGetPhotosCallback implements Callback<SearchResponse> {

    private static final String LOG_TAG = "FlickrGetPhotosCallback";
    private InspectPhotosCallback callback;

    public FlickrGetPhotosCallback(InspectPhotosCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
         if (response.isSuccessful()) {
             Log.i(LOG_TAG, "Received successful response.");

             SearchResponse searchResponse = response.body();
             List<FlickrPhoto> flickrPhotos = searchResponse.getPhotos();

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
    public void onFailure(Call<SearchResponse> call, Throwable t) {
        Log.e(LOG_TAG, "Failure:");
        t.printStackTrace();
    }

}
