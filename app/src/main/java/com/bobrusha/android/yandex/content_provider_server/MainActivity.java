package com.bobrusha.android.yandex.content_provider_server;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bobrusha.android.yandex.content_provider_server.network.MyService;

public class MainActivity extends AppCompatActivity {
    public static final String DEBUG_TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(DEBUG_TAG, "in activities onCreate");
        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);
    }

}
