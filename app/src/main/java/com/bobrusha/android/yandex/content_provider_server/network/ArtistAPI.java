package com.bobrusha.android.yandex.content_provider_server.network;

import com.bobrusha.android.yandex.content_provider_server.model.Artist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Aleksandra on 05/08/16.
 */
public interface ArtistAPI {

    @GET("mobilization-2016/artists.json")
    Call<List<Artist>> getListOfArtist();
}
