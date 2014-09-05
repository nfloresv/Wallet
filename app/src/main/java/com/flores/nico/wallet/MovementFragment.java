package com.flores.nico.wallet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
 *
 */
public class MovementFragment extends Fragment {
    private ArrayAdapter<Category> movementCategoryArray;
    private ArrayAdapter<CharSequence> movementTypeArray;
    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;*/

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovementFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovementFragment newInstance(/*String param1, String param2*/) {
        MovementFragment fragment = new MovementFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    /*public MovementFragment() {
        // Required empty public constructor
    }*/

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
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
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

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    /*@Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

    public void saveMovement(View view) {
        createMovement();

        resetView();

        Context context = getActivity().getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, "Test", duration);
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
        boolean type = movementType.getSelectedItem().toString() == getResources().getStringArray
                (R.array.movement_fragment_spinner_movement_type)[0];
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
