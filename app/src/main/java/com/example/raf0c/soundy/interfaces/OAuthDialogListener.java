package com.example.raf0c.soundy.interfaces;

/**
 * Created by raf0c on 14/09/15.
 */
public interface OAuthDialogListener {
    public abstract void onComplete(String accessToken);
    public abstract void onError(String error);
}
