package com.bobrusha.android.yandex.content_provider_server.db;

import android.database.Cursor;

/**
 * Created by Aleksandra on 10/08/16.
 */

public class DbUtils {

    public static Long getResultLongAndClose(Cursor c) {
        Long id = getResultLong(c, 0);
        closeCursor(c);
        return id;
    }

    public static Long getResultLong(Cursor c, int column) {
        if (c != null && c.moveToFirst()) {
            return c.isNull(column) ? null : c.getLong(column);
        }
        return null;
    }

    public static void closeCursor(Cursor c) {
        if (c != null && !c.isClosed()) {
            c.close();
        }
    }
}
