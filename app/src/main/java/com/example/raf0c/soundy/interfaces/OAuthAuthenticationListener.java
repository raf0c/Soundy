package com.example.raf0c.soundy.interfaces;

/**
 * Created by raf0c on 14/09/15.
 */
public interface OAuthAuthenticationListener {
    public abstract void onSuccess();
    public abstract void onFail(String error);
}
