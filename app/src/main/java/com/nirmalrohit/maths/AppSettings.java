package com.nirmalrohit.maths;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rohit on 12/19/2017.
 */

public class AppSettings {

    private String kSound;

    private Activity mActivity;
    private SharedPreferences mSharedPref;
    private Boolean mSound;

    public AppSettings (Activity activity) {
        this.kSound = activity.getString(R.string.saved_is_sound);
        this.mActivity = activity;
        this.mSharedPref = mActivity.getPreferences(Context.MODE_PRIVATE);
        this.mSound = mSharedPref.getBoolean(kSound, true);
    }

    public Boolean getSound() {
        return mSound;
    }

    public void setSound(Boolean mSound) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putBoolean(kSound, mSound);
        editor.commit();

        this.mSound = mSound;
    }
}
