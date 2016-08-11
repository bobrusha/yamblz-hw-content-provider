package com.bobrusha.android.yandex.content_provider_server;

import android.database.sqlite.SQLiteDatabase;

import com.bobrusha.android.yandex.content_provider_server.db.Contract;
import com.bobrusha.android.yandex.content_provider_server.db.DbBackend;
import com.bobrusha.android.yandex.content_provider_server.db.DbUtils;
import com.bobrusha.android.yandex.content_provider_server.db.MyOpenHelper;
import com.bobrusha.android.yandex.content_provider_server.model.Artist;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra on 10/08/16.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DbBackendInsertTest {

    private DbBackend dbBackend;
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        MyOpenHelper helper = new MyOpenHelper(RuntimeEnvironment.application);
        db = helper.getWritableDatabase();
        dbBackend = new DbBackend(helper);
    }

    @Test
    public void testInsertGenre() throws Exception {
        dbBackend.insertGenre(db, "qq");
        long actual = getCount(db, Contract.GenreEntry.TABLE_NAME);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testInsertArtistGenre() throws Exception {
        dbBackend.insertArtistGenre(db, 1, 1);
        long actual = getCount(db, Contract.GenreArtistEntry.TABLE_NAME);
        Assert.assertEquals(1, actual);
    }

    @Test
    public void testInsertArtist() throws Exception {
        Artist artist = TestUtils.getArtist(1);
        dbBackend.insertArtist(db, artist);
        long expected = 3;
        long actual = 0;
        actual += getCount(db, Contract.ArtistEntry.TABLE_NAME);
        actual += getCount(db, Contract.GenreArtistEntry.TABLE_NAME);
        actual += getCount(db, Contract.GenreEntry.TABLE_NAME);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testInsertArtists() throws Exception {
        List<Artist> artists = new ArrayList<>();
        artists.add(TestUtils.getArtist(1));
        artists.add(TestUtils.getArtist(2));

        dbBackend.insertArtists(artists);
        Assert.assertEquals(2, getCount(db, Contract.ArtistEntry.TABLE_NAME));
    }


    private int getCount(SQLiteDatabase db, String table) {
        return DbUtils.getResultLongAndClose(
                db.rawQuery("select count(*) from " + table, null)).intValue();
    }

}
