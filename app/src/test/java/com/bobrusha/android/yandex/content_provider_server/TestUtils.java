package com.bobrusha.android.yandex.content_provider_server;

import com.bobrusha.android.yandex.content_provider_server.model.Artist;
import com.bobrusha.android.yandex.content_provider_server.model.Cover;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Aleksandra on 10/08/16.
 */

public class TestUtils {

    public static Artist getArtist(int id) {
        Artist artist = new Artist();
        artist.setName(random());
        artist.setAlbums(10);
        artist.setTracks(20);
        artist.setDescription("description");
        artist.setId(id);
        List<String> genres = new LinkedList<>();
        genres.add("pop");
        artist.setGenres(genres);

        Cover cover = new Cover();
        cover.setBig("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000");
        cover.setSmall("http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300");
        artist.setCover(cover);
        return artist;
    }

    public static String random() {
        int maxLength = 20;
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(maxLength);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
