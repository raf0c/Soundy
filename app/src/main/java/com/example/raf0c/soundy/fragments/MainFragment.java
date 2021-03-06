package com.example.raf0c.soundy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.raf0c.soundy.R;
import com.example.raf0c.soundy.constants.Constants;
import com.example.raf0c.soundy.controller.ApplicationController;
import com.example.raf0c.soundy.interfaces.OAuthAuthenticationListener;
import com.example.raf0c.soundy.interfaces.OAuthDialogListener;
import com.example.raf0c.soundy.utils.BitmapLruCache;
import com.example.raf0c.soundy.views.DialogAuthSC;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


/**
 * Created by raf0c on 14/09/15.
 */
public class MainFragment extends Fragment implements View.OnClickListener{

    private LinearLayout myLayout;
    private Button mBtn_connect;
    private DialogAuthSC dialogAuthSC;
    private OAuthAuthenticationListener mListener;
    private TextView mTvname;
    private TextView mTvfull_name;
    private TextView mTvCountry;
    private NetworkImageView profile_pic;
    public ImageLoader mImageLoader;
    private String mAccessToken;
    private Boolean mUserSuccess;
    private static Context mContext;
    private String mUserID;
    private Fragment followersFragment;
    private String username;
    private String fullname;
    private String country;
    private String profpic;

    public MainFragment(){

    }


    public static Fragment newInstance(Context context) {
        MainFragment f = new MainFragment();
        mContext = context;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myLayout = (LinearLayout) inflater.inflate(R.layout.fragment_main, container, false);
        initElements();
        Log.i(Constants.TAG,Constants.mAuthUrl);
        OAuthDialogListener listener = new OAuthDialogListener() {
            @Override
            public void onComplete(String code) {
                requestAccessToken(code);
            }

            @Override
            public void onError(String error) {
                mListener.onFail("Authorization failed");
                Log.e("ERROR", error);
            }
        };
        dialogAuthSC = new DialogAuthSC(mContext, Constants.mAuthUrl,listener);
        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());
        if(savedInstanceState!=null){
            setUsername(savedInstanceState.getString(Constants.KEY_USERNAME));
            setFullname(savedInstanceState.getString(Constants.KEY_FULL_NAME));
            setCountry(savedInstanceState.getString(Constants.KEY_COUNTRY));
            setProfpic(savedInstanceState.getString(Constants.KEY_PROFPIC));

            mTvname.setText(getUsername());
            mTvfull_name.setText(getFullname());
            mTvCountry.setText(getCountry());
            profile_pic.setImageUrl(getProfpic(), mImageLoader);
            mUserSuccess = savedInstanceState.getBoolean(Constants.KEY_SUCCESS);

        }

        return myLayout;
    }

    public String getUsername() {return username;}

    private void setUsername(String username) {this.username = username;}

    public String getFullname() {return fullname;}

    private void setFullname(String fullname) {this.fullname = fullname;}

    public String getCountry() {return country;}

    private void setCountry(String country) {this.country = country;}

    public String getProfpic() {return profpic;}

    private void setProfpic(String profpic) {this.profpic = profpic;}

    @Override
    public void onViewStateRestored (Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {

            setUsername(savedInstanceState.getString(Constants.KEY_USERNAME));
            setFullname(savedInstanceState.getString(Constants.KEY_FULL_NAME));
            setCountry(savedInstanceState.getString(Constants.KEY_COUNTRY));
            setProfpic(savedInstanceState.getString(Constants.KEY_PROFPIC));

            mTvname.setText(getUsername());
            mTvfull_name.setText(getFullname());
            mTvCountry.setText(getCountry());
            profile_pic.setImageUrl(getProfpic(), mImageLoader);
            mUserSuccess = savedInstanceState.getBoolean(Constants.KEY_SUCCESS);

        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            setUsername(savedInstanceState.getString(Constants.KEY_USERNAME));
            setFullname(savedInstanceState.getString(Constants.KEY_FULL_NAME));
            setCountry(savedInstanceState.getString(Constants.KEY_COUNTRY));
            setProfpic(savedInstanceState.getString(Constants.KEY_PROFPIC));

            mTvname.setText(getUsername());
            mTvfull_name.setText(getFullname());
            mTvCountry.setText(getCountry());
            profile_pic.setImageUrl(getProfpic(), mImageLoader);
            mUserSuccess = savedInstanceState.getBoolean(Constants.KEY_SUCCESS);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.KEY_USERNAME, username);
        outState.putString(Constants.KEY_FULL_NAME, fullname);
        outState.putString(Constants.KEY_COUNTRY, country);
        outState.putString(Constants.KEY_PROFPIC, profpic);
        outState.putBoolean(Constants.KEY_SUCCESS, mUserSuccess);

    }


    private void initElements(){
        followersFragment = FollowersFragment.newInstance(mContext);
        mBtn_connect = (Button) myLayout.findViewById(R.id.btn_connectSC);
        mBtn_connect.setOnClickListener(this);
        mTvname = (TextView) myLayout.findViewById(R.id.sc_username);
        mTvfull_name = (TextView) myLayout.findViewById(R.id.sc_fullname);
        mTvCountry = (TextView) myLayout.findViewById(R.id.sc_country);
        profile_pic = (NetworkImageView) myLayout.findViewById(R.id.profile_pic);
        mImageLoader = new ImageLoader(ApplicationController.getInstance().getRequestQueue(), new BitmapLruCache());

    }

    @Override
    public void onClick(View v) {
        dialogAuthSC.show();
    }

    public void requestAccessToken(final String code) {

        String mAuthTokenURL = Constants.mAuthTokenURL+code;

        StringRequest request = new StringRequest(Request.Method.POST, mAuthTokenURL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Success Response = ", response);
                try{
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                    mAccessToken = jsonObj.getString("access_token");
                    Log.i(Constants.TAG, "Got access token: " + mAccessToken);
                    setmAccessToken(mAccessToken);
                    mUserSuccess = true;
                    fetchUserInfo(Constants.URL_ME, mAccessToken);
                }catch(Exception e){
                    e.printStackTrace();
                    mUserSuccess = false;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Response = ", error.toString());
            }
        });

        ApplicationController.getInstance().getRequestQueue().add(request);
    }

    public String getmUserID(){
        return mUserID;
    }
    private void setmUserID(String userID){
        mUserID = userID;
    }

    public String getmAccessToken(){
        return mAccessToken;
    }
    private void setmAccessToken(String token){
        mAccessToken = token;
    }


    private void fetchUserInfo(String url, String token) {

        JsonObjectRequest request = new JsonObjectRequest(url+token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            Log.e(Constants.TAG,jsonObject.toString());
                             username   = jsonObject.getString(Constants.TAG_USERNAME);
                             fullname = jsonObject.getString(Constants.TAG_FULLNAME);
                             country = jsonObject.getString(Constants.TAG_COUNTRY);
                             profpic = jsonObject.getString(Constants.TAG_AVATARURL);
                            setmUserID(jsonObject.getString(Constants.TAG_ID));
                            setInfoUser(username, fullname, country, profpic);
                        }
                        catch(JSONException e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        ApplicationController.getInstance().getRequestQueue().add(request);
    }

    public void saveData() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_ID, getmUserID());
        bundle.putString(Constants.KEY_TOKEN, getmAccessToken());
        followersFragment.setArguments(bundle);
    }

    public void setInfoUser(String user, String fullname,String country, String profpic){

        if(mUserSuccess){
            mBtn_connect.setVisibility(View.GONE);
        }
        else{
            mBtn_connect.setVisibility(View.GONE);
        }
        mTvname.setText(user);
        mTvfull_name.setText(fullname);
        mTvCountry.setText(country);
        profile_pic.setImageUrl(profpic, mImageLoader);

        saveData();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.followers_container, followersFragment).commit();


    }



}
