package org.chyla.photoapp.Main.Repository;

import android.util.Log;

import org.chyla.photoapp.Main.Repository.detail.FlickrAPI;
import org.chyla.photoapp.Main.Repository.detail.FlickrGetPhotosCallback;
import org.chyla.photoapp.Main.Repository.detail.objects.FlickrResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FlickrRepository implements InspectPhotosRepository {

    private static final String LOG_TAG = "FlickrRepository";
    private static final String BASE_API_URL = "https://api.flickr.com/services/rest/";
    private FlickrAPI api;

    public FlickrRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(FlickrAPI.class);
    }

    @Override
    public void getPhotosByTags(final List<String> tags, InspectPhotosCallback callback) {
        final String allTags = convertTagsToString(tags);

        Log.i(LOG_TAG, "Looking for photos with tags: " + allTags);

        Call<FlickrResponse> call = api.getPhotosByTags("", allTags);
        call.enqueue(new FlickrGetPhotosCallback(callback));
    }

    private String convertTagsToString(final List<String> tags) {
        String allTags = "";

        for (final String tag : tags) {
            allTags = allTags + tag + ",";
        }

        return allTags;
    }

}
