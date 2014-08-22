package com.flores.nico.wallet;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by nicoflores on 21-08-14.
 */
public class Credentials {
    private Context context;
    private SharedPreferences sharedPreferences;

    public Credentials(Context context) {
        this.context = context;
        String preferences_file = context.getString(R.string.preferences_credentials);
        sharedPreferences = context.getSharedPreferences(preferences_file, Context.MODE_PRIVATE);
    }

    public boolean setLoginData(String user_email, String user_password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.code_user_email), user_email);
        editor.putString(context.getString(R.string.code_user_password), user_password);
        return editor.commit();
    }

    public boolean isUserLoggedIn () {
        String default_value = context.getString(R.string.code_no_user_signed_in);
        String user_signed_in = sharedPreferences.getString(context.getString(R.string.code_user_email),
                default_value);
        return !user_signed_in.equalsIgnoreCase(default_value);
    }

    public String getUserEmail() {
        String default_value = context.getString(R.string.code_no_user_signed_in);
        return sharedPreferences.getString(context.getString(R.string.code_user_email),
                default_value);
    }

    public boolean setLogout() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        return editor.commit();
    }
}
