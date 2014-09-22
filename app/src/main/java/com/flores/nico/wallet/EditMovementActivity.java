package com.flores.nico.wallet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.flores.nico.adapters.category.CategorySpinnerAdapter;
import com.flores.nico.database.Category;
import com.flores.nico.database.Movement;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class EditMovementActivity extends Activity {
    private long movement_id;
    private EditText movementAmount;
    private Spinner movementType;
    private Spinner movementCategory;
    private EditText movementDescription;
    private static Date movementDate;

    public static final String MOVEMENT_ID = "com.flores.nico.wallet.MOVEMENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movement);

        movementAmount = (EditText) findViewById(R.id.editMovementActivityInputAmount);
        movementType = (Spinner) findViewById(R.id.editMovementActivityInputType);
        ArrayAdapter<CharSequence> movementTypeArray = ArrayAdapter.createFromResource(this,
                R.array.movement_fragment_spinner_movement_type, android.R.layout.simple_spinner_item);
        movementTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        movementType.setAdapter(movementTypeArray);
        movementCategory = (Spinner) findViewById(R.id.editMovementActivityInputCategory);
        List<Category> movementCategories = Category.listAll(Category.class);
        CategorySpinnerAdapter movementCategoryArray = new CategorySpinnerAdapter(this,
                R.layout.category_spinner_adapter, movementCategories);
        movementCategory.setAdapter(movementCategoryArray);
        EditText movementDatePicker = (EditText) findViewById(R.id
                .editMovementActivityInputDatePicker);
        movementDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                showDatePicker();
            }
        });
        movementDescription = (EditText) findViewById(R.id.editMovementActivityInputDescription);

        Button saveButton = (Button) findViewById(R.id.editMovementActivityBtnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                saveMovement();
            }
        });

        Intent intent = getIntent();
        movement_id = intent.getLongExtra(MOVEMENT_ID, 0);

        Movement movement = Movement.findById(Movement.class, movement_id);
        if (movement_id != 0 && movement != null) {
            movementAmount.setText(Double.toString(movement.getAmount()));
            if (!movement.isIncome()) {
                movementType.setSelection(1);
            }
            int position = movementCategoryArray.getPosition(movement.getCategory());
            movementCategory.setSelection(position);
            movementDate = movement.getMovement_date();
            movementDatePicker.setText(String.format("%1$ta %1$td %1$tb %1$tY", movementDate));
            movementDescription.setText(movement.getDescription());
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_movement, menu);
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

    public void saveMovement() {
        Toast toast;
        String message;
        int duration = Toast.LENGTH_SHORT;

        if (!movementAmount.getText().toString().isEmpty()) {
            double amount = Double.parseDouble(movementAmount.getText().toString());
            Category category = (Category) movementCategory.getSelectedItem();
            String selected_type = movementType.getSelectedItem().toString();
            String array_type = getResources().getStringArray(R.array
                    .movement_fragment_spinner_movement_type)[0];
            boolean type = array_type.equalsIgnoreCase(selected_type);
            String description = movementDescription.getText().toString();

            Movement movement = Movement.findById(Movement.class, movement_id);
            movement.setAmount(amount);
            movement.setCategory(category);
            movement.setIncome(type);
            movement.setMovement_date(movementDate);
            movement.setDescription(description);
            movement.save();

            message = getString(R.string.toast_edit_movement_activity_successful_update);
            toast = Toast.makeText(getApplicationContext(), message, duration);

            finish();
        } else {
            message = getString(R.string.toast_movement_fragment_movement_error);
            toast = Toast.makeText(getApplicationContext(), message, duration);
        }
        toast.show();
    }

    public void showDatePicker() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datepicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog
            .OnDateSetListener {

        @Override
        public Dialog onCreateDialog (Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            if (movementDate != null) {
                calendar.setTime(movementDate);
            }
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet (DatePicker datePicker, int year, int month, int day) {
            movementDate = new Date(datePicker.getCalendarView().getDate());
            EditText picker = (EditText) getActivity().findViewById(R.id.editMovementActivityInputDatePicker);
            picker.setText(String.format("%1$ta %1$td %1$tb %1$tY", movementDate));
        }

    }
}
