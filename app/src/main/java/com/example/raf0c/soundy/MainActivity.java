package com.example.raf0c.soundy;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.raf0c.soundy.constants.Constants;
import com.example.raf0c.soundy.controller.ApplicationController;
import com.example.raf0c.soundy.interfaces.OAuthAuthenticationListener;
import com.example.raf0c.soundy.interfaces.OAuthDialogListener;
import com.example.raf0c.soundy.views.DialogAuthSC;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button mBtn_connect;
    private DialogAuthSC dialogAuthSC;
    private String mAuthUrl;
    private OAuthAuthenticationListener mListener;
    private String mAccessToken;
    private String mCallbackUrl;
    private Boolean mUserSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCallbackUrl = Constants.URL_REDIRECT;
//        mAuthUrl = Constants.TOKEN_URL +
//                "?client_id=" + Constants.CLIENT_ID +
//                "?client_secret=" + Constants.CLIENT_SECRET +
//                "?grant_type=authorization_code" +
//                "?redirect_uri=" + Constants.URL_REDIRECT ;
        mAuthUrl = Constants.END_USER_AUTH +
                "?client_id=" + Constants.CLIENT_ID +
                "?redirect_uri=" + Constants.URL_REDIRECT +
                "?response_type=code" +
                "?scope='*'?display=popup";

        Uri.encode(mAuthUrl);
        // https://api.soundcloud.com/oauth2/token?client_id=e6ca2671f0795ff30d3645dcd8acadf9?redirect_uri=https://github.com/raf0c?response_type=code?scope='*'?display=popup

        Log.i(Constants.TAG,mAuthUrl);
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
        dialogAuthSC = new DialogAuthSC(this, mAuthUrl,listener);

        initElements();
    }

    private void initElements(){
            mBtn_connect = (Button) findViewById(R.id.btn_connectSC);
            mBtn_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialogAuthSC.show();
    }

    public void requestAccessToken(final String code) {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.TOKEN_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e("Success Response = ", response);
                try{
                    JSONObject jsonObj = (JSONObject) new JSONTokener(response).nextValue();

                    mAccessToken = jsonObj.getString("access_token");
                    Log.i(Constants.TAG, "Got access token: " + mAccessToken);

                    mUserSuccess = true;
                    //setInfo(user,name,profpic);
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
