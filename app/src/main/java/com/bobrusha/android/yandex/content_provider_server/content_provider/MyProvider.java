package com.bobrusha.android.yandex.content_provider_server.content_provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bobrusha.android.yandex.content_provider_server.db.Contract;
import com.bobrusha.android.yandex.content_provider_server.db.MyOpenHelper;

/**
 * Created by Aleksandra on 05/08/16.
 */
public class MyProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String provider = "com.bobrusha.yandex.provider";

    static {
        uriMatcher.addURI(provider, "artist", 1);
        uriMatcher.addURI(provider, "artist/#", 2);
    }

    private MyOpenHelper dbHelper;


    @Override
    public boolean onCreate() {
        dbHelper = new MyOpenHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {
            case 1:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "_ID ASC";
                }
                break;
            case 2:
                selection = selection + "_ID = " + uri.getLastPathSegment();
                break;
            default:
                //TODO: throw exception
        }

        dbHelper.getReadableDatabase().query(Contract.ArtistEntry.TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);

        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


}
