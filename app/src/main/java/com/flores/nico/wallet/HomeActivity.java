package com.flores.nico.wallet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class HomeActivity extends Activity {
    public static final int LOGIN_CODE = 0x00001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Credentials credentials = new Credentials(getApplicationContext());
        if (credentials.isUserLoggedIn()) {
            String user_email = credentials.getUserEmail();
            TextView userEmail = (TextView) findViewById(R.id.TVUserName);
            userEmail.setText(user_email);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_CODE);
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
        } else if (id == R.id.action_logout) {
            Credentials credentials = new Credentials(getApplicationContext());
            boolean result = credentials.setLogout();

            TextView userEmail = (TextView) findViewById(R.id.TVUserName);
            userEmail.setText("");

            return result;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_CODE) {
            if (resultCode == RESULT_OK) {
                String emailText = data.getStringExtra(LoginActivity.USER_EMAIL);
                String passwordText = data.getStringExtra(LoginActivity.USER_PASSWORD);

                TextView userEmail = (TextView) findViewById(R.id.TVUserName);
                userEmail.setText(emailText);

                Credentials credentials = new Credentials(getApplicationContext());
                credentials.setLoginData(emailText, passwordText);
            }
        }
    }
}
