package com.example.raf0c.soundy.constants;

/**
 * Created by raf0c on 14/09/15.
 */
public class Constants {

    public static final String CLIENT_ID = "e6ca2671f0795ff30d3645dcd8acadf9";
    public static final String CLIENT_SECRET = "b1f9a2fc243e2d21d82d6db72259d0d6";
    public static final String END_USER_AUTH = "https://soundcloud.com/connect";
    public static final String TOKEN_URL = "https://api.soundcloud.com/oauth2/token";
    public static final String URL_REDIRECT = "https://github.com/raf0c";
    public static final String TAG = "SoundCloudAPI";
    public static final String URL_ME="https://api.soundcloud.com/me?oauth_token=";
    public static final String mAuthUrl = Constants.END_USER_AUTH +
            "?client_id=" + Constants.CLIENT_ID +
            "&redirect_uri=" + Constants.URL_REDIRECT +
            "&response_type=code" +
            "&display=popup";
}
