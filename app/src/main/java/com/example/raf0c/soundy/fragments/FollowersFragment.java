package com.example.raf0c.soundy.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by raf0c on 15/09/15.
 */
public class FollowersFragment extends Fragment {

    private static Context mContext;

    public FollowersFragment(){

    }


    public static Fragment newInstance(Context context) {
        FollowersFragment f = new FollowersFragment();
        mContext = context;
        return f;
    }

}
