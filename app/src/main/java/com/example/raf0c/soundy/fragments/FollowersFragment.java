package com.example.raf0c.soundy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.raf0c.soundy.R;
import com.example.raf0c.soundy.adapters.ImageItemAdapter;
import com.example.raf0c.soundy.constants.Constants;
import com.example.raf0c.soundy.controller.ApplicationController;
import com.example.raf0c.soundy.model.ImageItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by raf0c on 15/09/15.
 */
public class FollowersFragment extends Fragment {

    private static Context mContext;
    private ImageItemAdapter mAdapter;
    private String mAccessToken;
    private RecyclerView rv;
    private List<ImageItem> imageRecords;

    public FollowersFragment(){}

    public static Fragment newInstance(Context context) {
        FollowersFragment f = new FollowersFragment();
        mContext = context;
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        mAccessToken = bundle.getString(Constants.KEY_TOKEN);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        LinearLayout myLayout = (LinearLayout) inflater.inflate(R.layout.fragment_followers, container, false);
        rv=(RecyclerView) myLayout.findViewById(R.id.cardList);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        imageRecords = new ArrayList<>();
        mAdapter = new ImageItemAdapter(getActivity(),imageRecords);
        rv.setAdapter(mAdapter);

        fetch(Constants.API_URL_ME + Constants.TAG_FOLLOWERS + "?oauth_token=" + mAccessToken);

        return myLayout;
    }


    private void fetch(String url) {
        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    imageRecords = parse(response);
                    mAdapter = new ImageItemAdapter(getActivity(),imageRecords);
                    rv.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        ApplicationController.getInstance().getRequestQueue().add(request);
    }

    private List<ImageItem> parse(JSONArray jsonArray) throws JSONException {

        ArrayList<ImageItem> records = new ArrayList<>();

        for(int i =0; i < jsonArray.length(); i++) {

            JSONObject data_obj = jsonArray.getJSONObject(i);

            String name = data_obj.getString(Constants.TAG_USERNAME);
            String url_picture =  data_obj.getString(Constants.TAG_AVATARURL);

            ImageItem row = new ImageItem(url_picture, name);
            records.add(row);
        }

        return records;
    }

}
