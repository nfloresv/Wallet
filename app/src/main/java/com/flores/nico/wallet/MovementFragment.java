package com.flores.nico.wallet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.flores.nico.database.Category;
import com.flores.nico.database.Movement;

import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovementFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovementFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class MovementFragment extends Fragment {
    private ArrayAdapter<Category> movementCategoryArray;
    private ArrayAdapter<CharSequence> movementTypeArray;

    public static MovementFragment newInstance() {
        MovementFragment fragment = new MovementFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create an ArrayAdapter using the string array and a default spinner layout
        movementTypeArray = ArrayAdapter.createFromResource(getActivity(),
                R.array.movement_fragment_spinner_movement_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        movementTypeArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<Category> movementCategories = Category.listAll(Category.class);
        movementCategoryArray = new ArrayAdapter<Category>(getActivity(),
                android.R.layout.simple_spinner_item, movementCategories);
        movementCategoryArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_movement, container, false);

        Spinner movementType = (Spinner) layout.findViewById(R.id.inputMovementType);
        // Apply the adapter to the spinner
        movementType.setAdapter(movementTypeArray);

        Spinner movementCategory = (Spinner) layout.findViewById(R.id.inputMovementCategory);
        movementCategory.setAdapter(movementCategoryArray);

        Button saveButton = (Button) layout.findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                saveMovement(view);
            }
        });
        return layout;
    }

    public void saveMovement(View view) {
        createMovement();

        resetView();

        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        String message = getString(R.string.toast_movement_fragment_movement_saved);
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void createMovement() {
        EditText movementAmount = (EditText) getActivity().findViewById(R.id.inputMovementAmount);
        Spinner movementCategory = (Spinner) getActivity().findViewById(R.id.inputMovementCategory);
        Spinner movementType = (Spinner) getActivity().findViewById(R.id.inputMovementType);
        DatePicker movementDate = (DatePicker) getActivity().findViewById(R.id.inputMovementDatePicker);
        EditText movementDescription = (EditText) getActivity().findViewById(R.id.inputMovementDescription);

        double amount = Double.parseDouble(movementAmount.getText().toString());
        Category category = Category.find(Category.class, "name = ?",
                movementCategory.getSelectedItem().toString()).get(0);
        String selected_type = movementType.getSelectedItem().toString();
        String array_type = getResources().getStringArray(R.array
                .movement_fragment_spinner_movement_type)[0];
        boolean type = array_type.equalsIgnoreCase(selected_type);
        Date date = new Date(movementDate.getCalendarView().getDate());
        String description = movementDescription.getText().toString();

        Movement movement = new Movement(amount, category, type, date, description);
        movement.save();
    }

    private void resetView() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, new MovementFragment())
                .commit();
    }
}
