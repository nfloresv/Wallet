package com.flores.nico.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.flores.nico.wallet.R;

/**
 * Created by nicoflores on 21-08-14.
 */
public class Credentials {
    private Context context;
    private SharedPreferences sharedPreferences;

    public Credentials (Context context) {
        this.context = context;
        String preferences_file = context.getString(R.string.preferences_credentials);
        sharedPreferences = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
    }

    public boolean setLoginData (String user_email, String user_password, String first_name,
                                 String last_name, int id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.code_user_email), user_email);
        editor.putString(context.getString(R.string.code_user_password), user_password);
        editor.putString(context.getString(R.string.code_user_first_name), first_name);
        editor.putString(context.getString(R.string.code_user_last_name), last_name);
        editor.putInt(context.getString(R.string.code_user_id), id);
        return editor.commit();
    }

    public boolean isUserLoggedIn () {
        String default_value = context.getString(R.string.code_no_user_signed_in);
        String user_signed_in = sharedPreferences.getString(context.getString(R.string.code_user_email),
                default_value);
        return !user_signed_in.equalsIgnoreCase(default_value);
    }

    public String getUserEmail () {
        String default_value = context.getString(R.string.code_no_user_signed_in);
        return sharedPreferences.getString(context.getString(R.string.code_user_email),
                default_value);
    }

    public boolean setLogout () {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }
}
