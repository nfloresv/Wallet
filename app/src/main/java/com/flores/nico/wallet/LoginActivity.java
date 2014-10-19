package com.flores.nico.wallet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.flores.nico.utils.VolleyClient;

import org.json.JSONObject;


public class LoginActivity extends Activity {
    public static final String USER_EMAIL = "com.flores.nico.wallet.EMAIL";
    public static final String USER_PASSWORD = "com.flores.nico.wallet.PASSWORD";
    public static final int SIGN_IN_ACTIVITY_REQUEST_CODE = 0x02;

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

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtras(data);

                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void loginActivityLogin (View view) {
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        EditText emailField = (EditText) findViewById(R.id.loginActivityInputEmail);
        CharSequence emailText = emailField.getText().toString();

        EditText passwordField = (EditText) findViewById(R.id.loginActivityInputPassword);
        String passwordText = passwordField.getText().toString();

        boolean validEmail = isEmailValid(emailText);

        if (validEmail && !passwordText.isEmpty()) {
            final Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra(USER_EMAIL, emailText);
            intent.putExtra(USER_PASSWORD, passwordText);

            final ProgressDialog dialog = ProgressDialog.show(this, "",
                    getString(R.string.toast_progress_dialog_loading), true, false);
            VolleyClient client = VolleyClient.getInstance(context);
            client.login(emailText.toString(), passwordText, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse (JSONObject response) {
                    Log.d("JSON success", response.toString());
                    dialog.dismiss();
                    String message = getString(R.string.toast_login_activity_login_successfully);
                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse (VolleyError error) {
                    Log.d("JSON Error", error.toString());
                    dialog.dismiss();
                    String message = getString(R.string.toast_login_activity_login_error);
                    Toast toast = Toast.makeText(context, message, duration);
                    toast.show();
                }
            });
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

    public void loginActivitySignIn (View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivityForResult(intent, SIGN_IN_ACTIVITY_REQUEST_CODE);
    }
}
