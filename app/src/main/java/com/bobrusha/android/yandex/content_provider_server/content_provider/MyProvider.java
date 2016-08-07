package com.bobrusha.android.yandex.content_provider_server.content_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.bobrusha.android.yandex.content_provider_server.db.Contract;
import com.bobrusha.android.yandex.content_provider_server.db.MyOpenHelper;

/**
 * Created by Aleksandra on 05/08/16.
 */
public class MyProvider extends ContentProvider {
    public static final String DEBUG_TAG = MyProvider.class.getName();

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public static final String provider = "com.bobrusha.android.yandex.content_provider_server.content_provider.MyProvider";

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

    /**
     * Get method
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(DEBUG_TAG, "in query");
        switch (uriMatcher.match(uri)) {
            case 1:
                Log.d(DEBUG_TAG, "Uri matches 1");
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = "_ID ASC";
                }
                break;
            case 2:
                selection = DatabaseUtils.concatenateWhere(selection, "_ID = " + ContentUris.parseId(uri));
                break;
            default:
                Log.d(DEBUG_TAG, "Uri doesn't match");
                return null;
        }

        Cursor cursor = dbHelper.getReadableDatabase().query(Contract.ArtistEntry.TABLE_NAME,
                projection, selection, selectionArgs, null, null, sortOrder);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Log.d(DEBUG_TAG, cursor.toString());
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        Log.d(DEBUG_TAG, "in insert");
        long row = dbHelper.getWritableDatabase().insertWithOnConflict(Contract.ArtistEntry.TABLE_NAME,
                null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        uri = Uri.parse(provider + "/artist/" + row);
        Log.d(DEBUG_TAG, uri.toString());
        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        switch (uriMatcher.match(uri)) {
            case 1:
                break;
            case 2:
                break;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String whereClause, String[] whereArgs) {
        switch (uriMatcher.match(uri)) {
            case 1:
                break;
            case 2:
                break;
            default:
                return -1;
        }
        return dbHelper.getWritableDatabase()
                .update(Contract.ArtistEntry.TABLE_NAME, contentValues, whereClause, whereArgs);
    }
}
