package com.bobrusha.android.yandex.content_provider_server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra on 05/08/16.
 */

public class Artist {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("genres")
    @Expose
    private List<String> genres = new ArrayList<String>();
    @SerializedName("tracks")
    @Expose
    private Integer tracks;
    @SerializedName("albums")
    @Expose
    private Integer albums;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("cover")
    @Expose
    private Cover cover;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The genres
     */
    public List<String> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    /**
     * @return The tracks
     */
    public Integer getTracks() {
        return tracks;
    }

    /**
     * @param tracks The tracks
     */
    public void setTracks(Integer tracks) {
        this.tracks = tracks;
    }

    /**
     * @return The albums
     */
    public Integer getAlbums() {
        return albums;
    }

    /**
     * @param albums The albums
     */
    public void setAlbums(Integer albums) {
        this.albums = albums;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The cover
     */
    public Cover getCover() {
        return cover;
    }

    /**
     * @param cover The cover
     */
    public void setCover(Cover cover) {
        this.cover = cover;
    }

    @Override
    public String toString() {
        return "Artist: " + id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (!id.equals(artist.id)) return false;
        if (!name.equals(artist.name)) return false;
        if (!genres.equals(artist.genres)) return false;
        if (!tracks.equals(artist.tracks)) return false;
        if (!albums.equals(artist.albums)) return false;
        if (link != null ? !link.equals(artist.link) : artist.link != null) return false;
        if (!description.equals(artist.description)) return false;
        return cover.equals(artist.cover);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + genres.hashCode();
        result = 31 * result + tracks.hashCode();
        result = 31 * result + albums.hashCode();
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + description.hashCode();
        result = 31 * result + cover.hashCode();
        return result;
    }
}

