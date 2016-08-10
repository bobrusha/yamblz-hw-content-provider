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
    public void testInsertArtist() throws Exception {
        Artist artist = TestUtils.getArtist(1);
        dbBackend.insertArtistCV(DbBackend.convertArtistToContentValues(artist));
        Assert.assertEquals(1, getCount(db, Contract.ArtistEntry.TABLE_NAME));
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
