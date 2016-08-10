package com.bobrusha.android.yandex.content_provider_server.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.bobrusha.android.yandex.content_provider_server.model.Artist;

import java.util.List;

/**
 * Created by Aleksandra on 10/08/16.
 */

public class DbBackend {

    private final MyOpenHelper helper;

    public DbBackend(Context context) {
        helper = new MyOpenHelper(context);
    }

    public DbBackend(MyOpenHelper helper) {
        this.helper = helper;
    }

    public Cursor getAllArtists() {
        return helper.getReadableDatabase().query(
                Contract.ArtistEntry.TABLE_NAME,
                null, null, null, null, null, null, null);
    }

    public Cursor getArtistByName(String name) {
        String selection = Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME + "=?";

        return helper.getReadableDatabase().query(
                Contract.ArtistEntry.TABLE_NAME,
                null,
                selection, new String[]{name},
                null, null, null, null);
    }


    public long insertArtistCV(SQLiteDatabase db, ContentValues values) {
        if (db.isReadOnly()) {
            return -1;
        }
        return db.insertWithOnConflict(Contract.ArtistEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long insertArtistCV(ContentValues values) {
        return insertArtistCV(helper.getWritableDatabase(), values);
    }

    public void insertArtists(List<Artist> artists) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Artist artist : artists) {
                insertArtistCV(db, convertArtistToContentValues(artist));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static final ContentValues convertArtistToContentValues(Artist artist) {
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
