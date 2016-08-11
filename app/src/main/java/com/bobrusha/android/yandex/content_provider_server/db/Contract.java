package com.bobrusha.android.yandex.content_provider_server.db;

import android.provider.BaseColumns;

/**
 * Created by Aleksandra on 05/08/16.
 */
public final class Contract {

    public static abstract class ArtistEntry implements BaseColumns {
        public static final String TABLE_NAME = "artist";
        public static final String COLUMN_NAME_ARTIST_NAME = "artist_name";
        public static final String COLUMN_NAME_GENRES = "genres";
        public static final String COLUMN_NAME_TRACKS = "amount_of_tracks";
        public static final String COLUMN_NAME_ALBUMS = "amount_of_albums";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COVER_SMALL = "cover_small";
        public static final String COLUMN_NAME_COVER_BIG = "cover_big";

        public static final String LIST_OF_GENRES = "list_of_genres";
    }

    public static abstract class GenreEntry implements BaseColumns {
        public static final String TABLE_NAME = "genre";
        public static final String COLUMN_GENRE_NAME = "genre_name";
    }

    public static abstract class GenreArtistEntry implements BaseColumns {
        public static final String TABLE_NAME = "genre_artist";
        public static final String COLUMN_NAME_GENRE_ID = "genre_id";
        public static final String COLUMN_NAME_ARTIST_ID = "artist_id";


    }
}
