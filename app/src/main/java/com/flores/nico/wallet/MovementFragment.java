package com.flores.nico.wallet;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.flores.nico.adapters.category.CategorySpinnerAdapter;
import com.flores.nico.database.Category;
import com.flores.nico.database.Movement;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovementFragment extends Fragment {
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0x03;
    private static Date movementDate;
    private CategorySpinnerAdapter movementCategoryArray;
    private ArrayAdapter<CharSequence> movementTypeArray;
    private Uri fileUri;

    public static MovementFragment newInstance () {
        MovementFragment fragment = new MovementFragment();
        return fragment;
    }

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Create an ArrayAdapter using the string array and a default spinner layout
        movementTypeArray = ArrayAdapter.createFromResource(getActivity(),
                R.array.movement_fragment_spinner_movement_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        movementTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<Category> movementCategories = Category.listAll(Category.class);
        movementCategoryArray = new CategorySpinnerAdapter(getActivity(),
                R.layout.category_spinner_adapter, movementCategories);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_movement, container, false);

        Spinner movementType = (Spinner) layout.findViewById(R.id.movementFragmentInputType);
        // Apply the adapter to the spinner
        movementType.setAdapter(movementTypeArray);

        Spinner movementCategory = (Spinner) layout.findViewById(R.id.movementFragmentInputCategory);
        movementCategory.setAdapter(movementCategoryArray);

        EditText datepicker = (EditText) layout.findViewById(R.id.movementFragmentInputDatePicker);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                showDatePicker();
            }
        });

        Button saveButton = (Button) layout.findViewById(R.id.movementFragmentBtnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                saveMovement();
            }
        });
        return layout;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.movement, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_movement_capture_image) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            fileUri = getOutputMediaFileUri();
            if (fileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            } else {
                int duration = Toast.LENGTH_SHORT;
                String message = getString(R.string.toast_movement_fragment_camera_error);
                Toast.makeText(getActivity().getApplicationContext(), message, duration).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Activity activity = getActivity();
        if (requestCode == MovementFragment.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            Toast toast;
            int duration = Toast.LENGTH_SHORT;
            String message;

            if (resultCode == Activity.RESULT_OK) {
                message = activity.getString(R.string.toast_movement_fragment_image_captured);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                message = getString(R.string.toast_movement_fragment_image_not_captured);
            } else {
                message = getString(R.string.toast_movement_fragment_image_error);
            }
            toast = Toast.makeText(activity.getApplicationContext(), message, duration);
            toast.show();
        }
    }

    private boolean createMovement () {
        EditText movementAmount = (EditText) getActivity().findViewById(R.id.movementFragmentInputAmount);
        Spinner movementCategory = (Spinner) getActivity().findViewById(R.id.movementFragmentInputCategory);
        Spinner movementType = (Spinner) getActivity().findViewById(R.id.movementFragmentInputType);
        EditText movementDescription = (EditText) getActivity().findViewById(R.id.movementFragmentInputDescription);

        if (!movementAmount.getText().toString().isEmpty()) {
            double amount = Double.parseDouble(movementAmount.getText().toString());
            Category category = (Category) movementCategory.getSelectedItem();
            String selected_type = movementType.getSelectedItem().toString();
            String array_type = getResources().getStringArray(R.array
                    .movement_fragment_spinner_movement_type)[0];
            boolean type = array_type.equalsIgnoreCase(selected_type);
            String description = movementDescription.getText().toString();

            Movement movement;
            if (fileUri != null) {
                movement = new Movement(amount, category, type, movementDate, description,
                        fileUri.getPath());
            } else {
                movement = new Movement(amount, category, type, movementDate, description);
            }
            movement.save();

            return true;
        } else {
            return false;
        }
    }

    private void resetView () {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new MovementFragment())
                .commit();
    }

    private Uri getOutputMediaFileUri () {
        return Uri.fromFile(getOutputMediaFile());
    }

    private File getOutputMediaFile () {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment
                    .DIRECTORY_PICTURES), "Wallet");
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Context context = getActivity().getApplicationContext();
                    mediaStorageDir = new File(context.getExternalFilesDir(Environment
                            .DIRECTORY_PICTURES), "Wallet");
                    if (!mediaStorageDir.exists()) {
                        if (!mediaStorageDir.mkdirs()) {
                            return null;
                        }
                    }
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            return new File(mediaStorageDir.getPath() + File.separator + "IMG_" +
                    timeStamp + ".jpg");
        }
        return null;
    }

    public void saveMovement () {
        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        String message;

        boolean result = createMovement();
        if (result) {
            message = getString(R.string.toast_movement_fragment_movement_saved);
            resetView();
        } else {
            message = getString(R.string.toast_movement_fragment_movement_error);
        }

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public void showDatePicker () {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datepicker");
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog
            .OnDateSetListener {

        @Override
        public Dialog onCreateDialog (Bundle savedInstanceState) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet (DatePicker datePicker, int year, int month, int day) {
            movementDate = new Date(datePicker.getCalendarView().getDate());
            EditText picker = (EditText) getActivity().findViewById(R.id.movementFragmentInputDatePicker);
            picker.setText(String.format("%1$ta %1$td %1$tb %1$tY", movementDate));
        }

    }
}
