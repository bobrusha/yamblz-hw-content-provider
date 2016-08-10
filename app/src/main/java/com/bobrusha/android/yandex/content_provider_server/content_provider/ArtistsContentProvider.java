package com.bobrusha.android.yandex.content_provider_server.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bobrusha.android.yandex.content_provider_server.db.DbBackend;
import com.bobrusha.android.yandex.content_provider_server.db.MyOpenHelper;
import com.bobrusha.android.yandex.content_provider_server.network.MyService;

/**
 * Created by Aleksandra on 05/08/16.
 */
public class ArtistsContentProvider extends ContentProvider {
    public static final String DEBUG_TAG = ArtistsContentProvider.class.getName();
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String provider = "com.bobrusha.android.yandex.content_provider_server.content_provider.ArtistsContentProvider";

    private static final int MATCH_ALL = 1;
    private static final int MATCH_ARTIST = 2;

    private DbBackend backend;

    static {
        uriMatcher.addURI(provider, "artist", 1);
        uriMatcher.addURI(provider, "artist/*", 2);
    }

    private MyOpenHelper dbHelper;

    @Override
    public boolean onCreate() {
        backend = new DbBackend(getContext());
        Intent intent = new Intent();
        intent.setClass(getContext(), MyService.class);
        getContext().startService(intent);
        return true;
    }


    /**
     * Get method
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(DEBUG_TAG, "in query");
        switch (uriMatcher.match(uri)) {
            case MATCH_ALL:
                return backend.getAllArtists();
            case MATCH_ARTIST:
                String artistName = uri.getLastPathSegment();
                return backend.getArtistByName(artistName);
            default:
                Log.d(DEBUG_TAG, "Uri doesn't match");
                return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        Uri resultUri = null;
        switch (uriMatcher.match(uri)) {
            case MATCH_ARTIST:
                long rowId = backend.insertArtistCV(contentValues);
                resultUri = Uri.parse(provider + "artist/" + rowId);
                break;
            default:
                throw new RuntimeException("Unsupported uri");
        }
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String whereClause, String[] whereArgs) {
        return 0;
    }


}
