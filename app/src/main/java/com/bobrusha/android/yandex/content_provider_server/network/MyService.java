package com.bobrusha.android.yandex.content_provider_server.network;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.bobrusha.android.yandex.content_provider_server.content_provider.ArtistsContentProvider;
import com.bobrusha.android.yandex.content_provider_server.db.Contract;
import com.bobrusha.android.yandex.content_provider_server.model.Artist;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aleksandra on 06/08/16.
 */
public class MyService extends IntentService {
    public static final String DEBUG_TAG = MyService.class.getName();
    private static final String NAME = MyService.class.getName();

    private final ArtistAPI api;
    private Retrofit retrofit;
    private OkHttpClient httpClient = new OkHttpClient();
    private Gson gson = new Gson();

    private static final String URL = "http://download.cdn.yandex.net/";

    public MyService() {
        super(NAME);
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(ArtistAPI.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(DEBUG_TAG, "in onHandleIntent");
        Call<List<Artist>> call = api.getListOfArtist();
        final List<Artist> result = new ArrayList<>();

        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                result.addAll(response.body());
                Log.d(DEBUG_TAG, "Size of " + result.size());

                for (Artist artist : result) {
                    Uri uri = Uri.parse("content://" + ArtistsContentProvider.provider + "/artist/" + artist.getId());
                    Log.d(DEBUG_TAG, artist.toString());
                    getContentResolver().insert(uri, getContentValuesFromArtist(artist));
                }
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public ContentValues getContentValuesFromArtist(Artist artist) {
        ContentValues values = new ContentValues();
        values.put(Contract.ArtistEntry._ID, artist.getId());
        values.put(Contract.ArtistEntry.COLUMN_NAME_GENRES, TextUtils.join(", ", artist.getGenres()));
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME, artist.getName());
        values.put(Contract.ArtistEntry.COLUMN_NAME_TRACKS, artist.getTracks());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ALBUMS, artist.getAlbums());
        values.put(Contract.ArtistEntry.COLUMN_NAME_LINK, artist.getLink());
        values.put(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION, artist.getDescription());

        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL, artist.getCover().getSmall());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG, artist.getCover().getBig());

        return values;
    }
}
