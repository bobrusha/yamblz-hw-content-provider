package com.bobrusha.android.yandex.content_provider_server;

import android.app.Application;
import android.content.Intent;

import com.bobrusha.android.yandex.content_provider_server.network.MyService;

/**
 * Created by Aleksandra on 06/08/16.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }
}
