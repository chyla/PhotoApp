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
    private static String BASE_API_URL;
    private final String API_KEY;
    private FlickrService service;

    public FlickrRepository(final String api_key) {
        this(api_key, "https://api.flickr.com/services/rest/");
    }

    public FlickrRepository(final String api_key, final String base_api_url) {
        API_KEY = api_key;
        BASE_API_URL = base_api_url;

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

        if (allTags.length() > 0) {
            allTags = allTags.substring(0, allTags.length() - 1);
        }

        return allTags;
    }

}
