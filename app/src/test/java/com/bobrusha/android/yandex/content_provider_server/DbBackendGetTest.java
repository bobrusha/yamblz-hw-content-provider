package com.bobrusha.android.yandex.content_provider_server;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bobrusha.android.yandex.content_provider_server.db.Contract;
import com.bobrusha.android.yandex.content_provider_server.db.DbBackend;
import com.bobrusha.android.yandex.content_provider_server.db.MyOpenHelper;
import com.bobrusha.android.yandex.content_provider_server.model.Artist;
import com.bobrusha.android.yandex.content_provider_server.model.Cover;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aleksandra on 10/08/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DbBackendGetTest {
    private DbBackend dbBackend;
    private SQLiteDatabase db;
    private List<Artist> expectedArtists;

    @Before
    public void setUp() throws Exception {
        MyOpenHelper helper = new MyOpenHelper(RuntimeEnvironment.application);
        db = helper.getWritableDatabase();
        dbBackend = new DbBackend(helper);

        expectedArtists = new LinkedList<>();
        expectedArtists.add(TestUtils.getArtist(1));
        expectedArtists.add(TestUtils.getArtist(2));

        dbBackend.insertArtists(expectedArtists);
    }

    @Test
    public void testGetArtistByName() throws Exception {
        final Cursor cursor = dbBackend.getArtistByName(expectedArtists.get(0).getName());
        cursor.moveToFirst();
        System.out.print(cursor.getString(cursor.getColumnIndex(Contract.ArtistEntry.LIST_OF_GENRES)));
        System.out.println(Arrays.deepToString(cursor.getColumnNames()));
        final Artist expectedArtist = getArtistFromCursor(cursor);
        Assert.assertEquals(expectedArtists.get(0), expectedArtist);
    }

    @Test
    public void testGetAllArtists() throws Exception {
        final Cursor cursor = dbBackend.getAllArtists();

        final List<Artist> actualArtists = new LinkedList<>();

        cursor.moveToFirst();
        do {
            actualArtists.add(getArtistFromCursor(cursor));
        } while (cursor.moveToNext());

        Assert.assertArrayEquals(expectedArtists.toArray(), actualArtists.toArray());
    }

    private Artist getArtistFromCursor(Cursor c) {
        Artist artist = new Artist();

        artist.setId(c.getInt(c.getColumnIndex(Contract.ArtistEntry._ID)));
        artist.setName(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME)));
        artist.setAlbums(c.getInt(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ALBUMS)));
        artist.setDescription(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION)));
        String[] genres = c.getString(c.getColumnIndex(Contract.ArtistEntry.LIST_OF_GENRES)).split(",", -1);
        artist.setGenres(Arrays.asList(genres));
        artist.setTracks(c.getInt(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_TRACKS)));
        artist.setLink(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_LINK)));

        Cover cover = new Cover();
        cover.setSmall(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL)));
        cover.setBig(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG)));
        artist.setCover(cover);

        return artist;
    }
}
