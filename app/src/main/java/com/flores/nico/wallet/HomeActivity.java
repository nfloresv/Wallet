package com.flores.nico.wallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class HomeActivity extends Activity {
    public static final int LOGIN_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String default_value = getString(R.string.no_user_signed_in);
        String user_signed_in = sharedPreferences.getString(getString(R.string.user_email),
                default_value);
        if (user_signed_in.equalsIgnoreCase(default_value)) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_CODE);
        } else {
            TextView userEmail = (TextView) findViewById(R.id.TVUserName);
            userEmail.setText(user_signed_in);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_CODE) {
            if (resultCode == RESULT_OK) {
                String emailText = data.getStringExtra(LoginActivity.USER_EMAIL);
                String passwordText = data.getStringExtra(LoginActivity.USER_PASSWORD);

                TextView userEmail = (TextView) findViewById(R.id.TVUserName);
                userEmail.setText(emailText);

                SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.user_email), emailText);
                editor.putString(getString(R.string.user_password), passwordText);
                editor.commit();
            }
        }
    }
}
