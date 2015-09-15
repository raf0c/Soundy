package com.example.raf0c.soundy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.toolbox.ImageLoader;
import com.example.raf0c.soundy.R;
import com.example.raf0c.soundy.constants.Constants;
import com.example.raf0c.soundy.controller.ApplicationController;
import com.example.raf0c.soundy.interfaces.OAuthDialogListener;
import com.example.raf0c.soundy.utils.BitmapLruCache;
import com.example.raf0c.soundy.views.DialogAuthSC;

/**
 * Created by raf0c on 15/09/15.
 */
public class FollowersFragment extends Fragment {

    private static Context mContext;
    private LinearLayout myLayout;
    private ImageLoader mImageLoader;

    public FollowersFragment(){

    }


    public static Fragment newInstance(Context context) {
        FollowersFragment f = new FollowersFragment();
        mContext = context;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myLayout = (LinearLayout) inflater.inflate(R.layout.fragment_followers, container, false);
        initElements();

        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());


        return myLayout;
    }

    public void initElements(){

    }

}
