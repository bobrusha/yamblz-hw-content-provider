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
    private static final String ARTIST_FULL = Contract.ArtistEntry.TABLE_NAME +
            " JOIN " + Contract.GenreArtistEntry.TABLE_NAME + " ON " +
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry._ID + "=" +
            Contract.GenreArtistEntry.TABLE_NAME + "." + Contract.GenreArtistEntry.COLUMN_NAME_ARTIST_ID +
            " JOIN " + Contract.GenreEntry.TABLE_NAME +
            " ON " + Contract.GenreEntry.TABLE_NAME + "." + Contract.GenreEntry._ID + "=" +
            Contract.GenreArtistEntry.TABLE_NAME + "." + Contract.GenreArtistEntry.COLUMN_NAME_GENRE_ID;

    private String[] COLUMNS = new String[]{
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry._ID,
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME,
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry.COLUMN_NAME_TRACKS,
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry.COLUMN_NAME_ALBUMS,
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION,
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry.COLUMN_NAME_LINK,
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL,
            Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry.COLUMN_NAME_COVER_BIG,
            "GROUP_CONCAT(" + Contract.GenreEntry.TABLE_NAME + "." + Contract.GenreEntry.COLUMN_GENRE_NAME +
                    ") AS " + Contract.ArtistEntry.LIST_OF_GENRES
    };
    ;

    public DbBackend(Context context) {
        helper = new MyOpenHelper(context);
        helper.setWriteAheadLoggingEnabled(true);
    }

    public DbBackend(MyOpenHelper helper) {
        this.helper = helper;
        this.helper.setWriteAheadLoggingEnabled(true);
    }

    public Cursor getAllArtists() {
        String groupBy = Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry._ID;
        return helper.getReadableDatabase().query(
                ARTIST_FULL,
                COLUMNS, null, null, groupBy, null, null, null);
    }

    public Cursor getArtistByName(String name) {
        String selection = Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME + "=?";
        String groupBy = Contract.ArtistEntry.TABLE_NAME + "." + Contract.ArtistEntry._ID;

        return helper.getReadableDatabase().query(
                ARTIST_FULL,
                COLUMNS,
                selection, new String[]{name},
                groupBy, null, null, null);
    }


    public long insertArtist(SQLiteDatabase db, Artist artist) {
        if (db.isReadOnly()) {
            return -1;
        }
        long resultId = -1;
        db.beginTransaction();
        try {
            //inserting artist
            ContentValues values = convertArtistToContentValues(artist);
            long artistId = db.insertWithOnConflict(Contract.ArtistEntry.TABLE_NAME, null,
                    values,
                    SQLiteDatabase.CONFLICT_IGNORE);

            //inserting genres
            for (String genre : artist.getGenres()) {
                //inserting to Genre table
                long genreId = insertGenre(db, genre);

                //inserting into artist_genre table
                insertArtistGenre(db, artistId, genreId);
            }

            resultId = artistId;
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return resultId;
    }

    public void insertArtists(List<Artist> artists) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (Artist artist : artists) {
                insertArtist(db, artist);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public long insertGenre(SQLiteDatabase db, String genre) {
        ContentValues genreCV = new ContentValues();
        genreCV.put(Contract.GenreEntry.COLUMN_GENRE_NAME, genre);
        return db.insertWithOnConflict(
                Contract.GenreEntry.TABLE_NAME, null, genreCV,
                SQLiteDatabase.CONFLICT_IGNORE);
    }

    public long insertArtistGenre(SQLiteDatabase db, long artistId, long genreId) {
        ContentValues artistGenreCV = new ContentValues();
        artistGenreCV.put(Contract.GenreArtistEntry.COLUMN_NAME_ARTIST_ID, artistId);
        artistGenreCV.put(Contract.GenreArtistEntry.COLUMN_NAME_GENRE_ID, genreId);
        return db.insertWithOnConflict(Contract.GenreArtistEntry.TABLE_NAME, null,
                artistGenreCV, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static ContentValues convertArtistToContentValues(Artist artist) {
        ContentValues values = new ContentValues();
        values.put(Contract.ArtistEntry._ID, artist.getId());
        values.put(Contract.ArtistEntry.COLUMN_NAME_GENRES, TextUtils.join(", ", artist.getGenres()));
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME, artist.getName());
        values.put(Contract.ArtistEntry.COLUMN_NAME_TRACKS, artist.getTracks());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ALBUMS, artist.getAlbums());
        values.put(Contract.ArtistEntry.COLUMN_NAME_LINK, artist.getLink());
        values.put(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION, artist.getDescription());

        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG, artist.getCover().getBig());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL, artist.getCover().getSmall());

        return values;
    }

}
