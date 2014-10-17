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

public class SignInActivity extends Activity {
    public static final String USER_FIRST_NAME = "com.flores.nico.wallet.FIRST_NAME";
    public static final String USER_LAST_NAME = "com.flores.nico.wallet.LAST_NAME";

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void signInActivitySignIn (View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        EditText firstNameField = (EditText) findViewById(R.id.signInActivityInputFirstName);
        String firstNameText = firstNameField.getText().toString();

        EditText lastNameField = (EditText) findViewById(R.id.signInActivityInputLastName);
        String lastNameText = lastNameField.getText().toString();

        EditText emailField = (EditText) findViewById(R.id.signInActivityInputEmail);
        CharSequence emailText = emailField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.signInActivityInputPassword);
        String passwordText = passwordField.getText().toString();

        boolean validEmail = isEmailValid(emailText);

        if (validEmail && !passwordText.isEmpty() && !firstNameText.isEmpty() && !lastNameText.isEmpty()) {
            String message = getString(R.string.toast_login_activity_login_successfully);
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(LoginActivity.USER_EMAIL, emailText);
            intent.putExtra(LoginActivity.USER_PASSWORD, passwordText);
            intent.putExtra(USER_FIRST_NAME, firstNameText);
            intent.putExtra(USER_LAST_NAME, lastNameText);

            /* TODO use volley to register new user */
            /*TODO show loading dialog*/
            setResult(RESULT_OK, intent);
            finish();
        } else if (!validEmail) {
            String message = getString(R.string.toast_login_activity_invalid_email);
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        } else if (passwordText.isEmpty()) {
            String message = getString(R.string.toast_login_activity_invalid_password);
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        } else {
            String message = "Please fill all the fields";
            Toast toast = Toast.makeText(context, message, duration);
            toast.show();
        }
    }

    private boolean isEmailValid (CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
