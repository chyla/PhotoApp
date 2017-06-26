package org.chyla.photoapp.Main.Repository.detail;

import org.chyla.photoapp.Main.Repository.detail.objects.FlickrResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrAPI {

    @GET("?method=flickr.photos.search&extras=description")
    Call<FlickrResponse> getPhotosByTags(@Query("api_key") String apiKey,
                                         @Query("tags") String allTags);

}
