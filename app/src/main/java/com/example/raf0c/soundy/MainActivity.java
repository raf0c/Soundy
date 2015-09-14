package com.example.raf0c.soundy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.example.raf0c.soundy.constants.Constants;
import com.example.raf0c.soundy.controller.ApplicationController;
import com.example.raf0c.soundy.interfaces.OAuthAuthenticationListener;
import com.example.raf0c.soundy.interfaces.OAuthDialogListener;
import com.example.raf0c.soundy.utils.BitmapLruCache;
import com.example.raf0c.soundy.views.DialogAuthSC;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button mBtn_connect;
    private DialogAuthSC dialogAuthSC;
    private OAuthAuthenticationListener mListener;
    private String mAccessToken;
    private String mCallbackUrl;
    private Boolean mUserSuccess = false;

    private TextView mTvname;
    private TextView mTvfull_name;
    private TextView mTvCountry;
    private NetworkImageView profile_pic;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        dialogAuthSC = new DialogAuthSC(this, Constants.mAuthUrl,listener);

        initElements();
    }

    private void initElements(){
            mBtn_connect = (Button) findViewById(R.id.btn_connectSC);
            mBtn_connect.setOnClickListener(this);
            mTvname = (TextView) findViewById(R.id.sc_username);
            mTvfull_name = (TextView) findViewById(R.id.sc_fullname);
            mTvCountry = (TextView) findViewById(R.id.sc_country);
            profile_pic = (NetworkImageView) findViewById(R.id.profile_pic);
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

    private void fetchUserInfo(String url, String token) {

        JsonObjectRequest request = new JsonObjectRequest(url+token, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {

                            Log.e(Constants.TAG,jsonObject.toString());
                            String username   = jsonObject.getString("username");
                            String fullname = jsonObject.getString("full_name");
                            String country = jsonObject.getString("country");
                            String profpic = jsonObject.getString("avatar_url");

                            setInfoUser(username,fullname,country,profpic);
                        }
                        catch(JSONException e) {
                            Toast.makeText(getApplicationContext(), "Unable to parse data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        Toast.makeText(getApplicationContext(), "Unable to fetch data: " + volleyError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        ApplicationController.getInstance().getRequestQueue().add(request);
    }

    private void setInfoUser(String user, String fullname,String country, String profpic){

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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
