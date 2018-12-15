package com.example.abdullahkabir.myapplication;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Abdullah Kabir on 2/16/2018.
 */

public class NetworkConnectionManager {

    private static OkHttpClient client;

    public static OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    public static boolean isConnectedToInternet(Context context) {


        return false;
    }

}
