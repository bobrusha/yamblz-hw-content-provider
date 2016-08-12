package com.bobrusha.android.yandex.content_provider_server.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Aleksandra on 05/08/16.
 */
public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String DEBUG_TAG = MyOpenHelper.class.getName();

    public static final String DB_NAME = "artists.db";
    public static final int VERSION = 3;

    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String SEP = ", ";
    private static final String FOREIGN_KEY = "FOREIGN KEY( ";
    private static final String REFERENCES = " REFERENCES ";

    private static final String CREATE_ARTIST_TABLE = CREATE_TABLE + Contract.ArtistEntry.TABLE_NAME +
            " (" + Contract.ArtistEntry._ID + INTEGER_TYPE + PRIMARY_KEY + SEP +
            Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_GENRES + INTEGER_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_TRACKS + INTEGER_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_ALBUMS + INTEGER_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_LINK + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_COVER_BIG + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL + TEXT_TYPE +
            " );";

    private static final String CREATE_GENRE_TABLE = CREATE_TABLE + Contract.GenreEntry.TABLE_NAME +
            " (" + Contract.GenreEntry._ID + INTEGER_TYPE + PRIMARY_KEY + SEP +
            Contract.GenreEntry.COLUMN_GENRE_NAME + TEXT_TYPE +
            ");";


    private static final String CREATE_GENRE_ARTIST_TABLE = CREATE_TABLE +
            Contract.GenreArtistEntry.TABLE_NAME + "(" +
            Contract.GenreArtistEntry._ID + INTEGER_TYPE + PRIMARY_KEY + SEP +
            Contract.GenreArtistEntry.COLUMN_NAME_GENRE_ID + INTEGER_TYPE + SEP +
            Contract.GenreArtistEntry.COLUMN_NAME_ARTIST_ID + INTEGER_TYPE + SEP +
            FOREIGN_KEY + Contract.GenreArtistEntry.COLUMN_NAME_GENRE_ID + ")" + REFERENCES +
            Contract.GenreEntry.TABLE_NAME + "(" + Contract.GenreEntry._ID + ")" + SEP +
            FOREIGN_KEY + Contract.GenreArtistEntry.COLUMN_NAME_ARTIST_ID + ")" + REFERENCES +
            Contract.ArtistEntry.TABLE_NAME + "(" + Contract.ArtistEntry._ID + ")" +
            ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.ArtistEntry.TABLE_NAME;

    private static final String ARTIST_NAME_INDEX = "artist_name_index";
    private static final String CREATE_ARTIST_NAME_INDEX = "CREATE INDEX " + ARTIST_NAME_INDEX +
            " ON " + Contract.ArtistEntry.TABLE_NAME + " (" + Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME +
            ");";

    public MyOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_ARTIST_TABLE);
        sqLiteDatabase.execSQL(CREATE_GENRE_TABLE);
        sqLiteDatabase.execSQL(CREATE_GENRE_ARTIST_TABLE);
        sqLiteDatabase.execSQL(CREATE_ARTIST_NAME_INDEX);

        Log.d(DEBUG_TAG, CREATE_ARTIST_TABLE);
        Log.d(DEBUG_TAG, CREATE_GENRE_TABLE);
        Log.d(DEBUG_TAG, CREATE_GENRE_ARTIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
