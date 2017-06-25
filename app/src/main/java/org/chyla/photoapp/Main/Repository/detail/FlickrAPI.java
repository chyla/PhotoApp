package org.chyla.photoapp.Main.Repository.detail;

import org.chyla.photoapp.Main.Repository.detail.objects.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FlickrAPI {

    @GET("?method=flickr.photos.search")
    Call<SearchResponse> getPhotosByTags(@Query("api_key") String apiKey,
                                         @Query("tags") String allTags);

}
