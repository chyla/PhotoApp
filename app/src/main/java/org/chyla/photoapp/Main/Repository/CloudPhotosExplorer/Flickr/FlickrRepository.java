package org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr;

import android.util.Log;

import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.CloudPhotosExplorerRepository;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.GetPhotosCallback;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail.FlickrService;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail.FlickrGetPhotosCallback;
import org.chyla.photoapp.Main.Repository.CloudPhotosExplorer.Flickr.detail.objects.FlickrResponse;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FlickrRepository implements CloudPhotosExplorerRepository {

    private static final String LOG_TAG = "FlickrRepository";
    private static final String BASE_API_URL = "https://api.flickr.com/services/rest/";
    private final String API_KEY;
    private FlickrService service;

    public FlickrRepository(final String api_key) {
        API_KEY = api_key;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_API_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .client(client)
                .build();

        service = retrofit.create(FlickrService.class);
    }

    @Override
    public void getPhotosByTags(final List<String> tags, GetPhotosCallback callback) {
        final String allTags = convertTagsToString(tags);

        Log.i(LOG_TAG, "Looking for photos with tags: " + allTags);

        Call<FlickrResponse> call = service.getPhotosByTags(API_KEY, allTags);
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