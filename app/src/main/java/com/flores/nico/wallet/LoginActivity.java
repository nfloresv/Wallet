package com.flores.nico.wallet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends Activity {
    public static final String USER_EMAIL = "com.flores.nico.wallet.EMAIL";
    public static final String USER_PASSWORD = "com.flores.nico.wallet.PASSWORD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
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

    public void login(View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        EditText emailField = (EditText) findViewById(R.id.inputLoginEmail);
        CharSequence emailText = emailField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.inputLoginPassword);
        String passwordText = passwordField.getText().toString();

        boolean validEmail = isEmailValid(emailText);

        if (validEmail && !passwordText.isEmpty()) {
            String message = getString(R.string.toast_login_activity_login_successfully);
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();

            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(USER_EMAIL, emailText);
            intent.putExtra(USER_PASSWORD, passwordText);

            setResult(RESULT_OK, intent);
            finish();
        }
        else if (!validEmail){
            String message = getString(R.string.toast_login_activity_invalid_email);
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        }
        else {
            String message = getString(R.string.toast_login_activity_invalid_password);
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
