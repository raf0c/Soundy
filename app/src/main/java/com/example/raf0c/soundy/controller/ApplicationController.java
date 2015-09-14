package com.example.raf0c.soundy.controller;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by raf0c on 14/09/15.
 */
public class ApplicationController extends Application {

    private static ApplicationController sInstance;

    private RequestQueue mRequestQueue;

    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);
        sInstance = this;
    }

    public synchronized static ApplicationController getInstance() {
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

}
