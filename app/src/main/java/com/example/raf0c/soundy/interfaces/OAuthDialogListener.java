package com.example.raf0c.soundy.interfaces;

/**
 * Created by raf0c on 14/09/15.
 */
public interface OAuthDialogListener {
     void onComplete(String accessToken);
     void onError(String error);
}
