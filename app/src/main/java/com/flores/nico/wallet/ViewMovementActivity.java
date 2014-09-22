package com.flores.nico.wallet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.flores.nico.database.Movement;


public class ViewMovementActivity extends Activity {
    private long movement_id;

    public static String MOVEMENT_ID = "com.flores.nico.wallet.MOVEMENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movement);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        movement_id = intent.getLongExtra(MOVEMENT_ID, 0);

        Movement movement = Movement.findById(Movement.class, movement_id);
        if (movement_id != 0 || movement != null) {

            TextView movementDate = (TextView) findViewById(R.id.viewMovementActivityDate);
            TextView movementAmount = (TextView) findViewById(R.id.viewMovementActivityAmount);
            TextView movementCategory = (TextView) findViewById(R.id.viewMovementActivityCategory);
            TextView movementDescription = (TextView) findViewById(R.id
                    .viewMovementActivityDescription);
            TextView movementImage = (TextView) findViewById(R.id.viewMovementActivityImage);

            String date = String.format("%1$ta %1$td %1$tb %1$tY", movement.getMovement_date());
            movementDate.setText(date);
            movementAmount.setText("$" + Double.toString(movement.getAmount()));
            if (!movement.isIncome()) {
                movementAmount.setTextColor(Color.parseColor("#FA5858"));
            } else {
                movementAmount.setTextColor(Color.parseColor("#BDBDBD"));
            }
            movementCategory.setText(movement.getCategory().getName());
            movementDescription.setText(movement.getDescription());
            movementImage.setText(movement.getFilePath());
        } else {
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_movement, menu);
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
        } else if (id == R.id.action_view_movement_edit) {
            Context context = getApplicationContext();

            Intent intent = new Intent(context, EditMovementActivity.class);
            intent.putExtra(EditMovementActivity.MOVEMENT_ID, movement_id);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
