package com.bobrusha.android.yandex.content_provider_server.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Aleksandra on 10/08/16.
 */

public class Cover {

    @SerializedName("small")
    @Expose
    private String small;
    @SerializedName("big")
    @Expose
    private String big;

    /**
     * @return The small
     */
    public String getSmall() {
        return small;
    }

    /**
     * @param small The small
     */
    public void setSmall(String small) {
        this.small = small;
    }

    /**
     * @return The big
     */
    public String getBig() {
        return big;
    }

    /**
     * @param big The big
     */
    public void setBig(String big) {
        this.big = big;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cover cover = (Cover) o;

        if (!small.equals(cover.small)) return false;
        return big.equals(cover.big);

    }

    @Override
    public int hashCode() {
        int result = small.hashCode();
        result = 31 * result + big.hashCode();
        return result;
    }
}