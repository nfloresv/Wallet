package com.flores.nico.wallet;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.flores.nico.adapters.movement.MovementListAdapter;
import com.flores.nico.database.Movement;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AllMovementsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMovementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AllMovementsFragment extends Fragment {
    private List<Movement> allMovements;
    private double allMovementsBalance;

    /*private OnFragmentInteractionListener mListener;*/

    public static AllMovementsFragment newInstance() {
        AllMovementsFragment fragment = new AllMovementsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        allMovements = Movement.listAll(Movement.class);
        allMovementsBalance = 0;
        for (Movement movement : allMovements) {
            if (movement.isIncome()) {
                allMovementsBalance += movement.getAmount();
            } else {
                allMovementsBalance -= movement.getAmount();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_all_movements, container, false);

        TextView tvAllMovementsBalance = (TextView) layout.findViewById(R.id
                .allMovementsFragmentTvBalance);
        tvAllMovementsBalance.setText("$" + Double.toString(allMovementsBalance));
        if (allMovementsBalance < 0) {
            tvAllMovementsBalance.setTextColor(Color.parseColor("#FA5858"));
        }

        ListView lvAllMovements = (ListView) layout.findViewById(R.id.allMovementsFragmentLvAllMovements);
        MovementListAdapter adapter = new MovementListAdapter(getActivity().getApplicationContext(),
                R.layout.movement_list_adapter, allMovements);
        lvAllMovements.setAdapter(adapter);
        return layout;
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    /*@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
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

}
