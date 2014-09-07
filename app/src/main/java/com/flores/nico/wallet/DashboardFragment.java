package com.flores.nico.wallet;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
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
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DashboardFragment extends Fragment {
    private List<Movement> dashboardMovements;
    private double dashboardBalance;
    private double dashboardIncome;
    private double dashboardOutcome;

    /*private OnFragmentInteractionListener mListener;*/

    public static DashboardFragment newInstance() {
        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardMovements = Movement.listAll(Movement.class);
        dashboardBalance = 0;
        dashboardIncome = 0;
        dashboardOutcome = 0;
        for (Movement movement : dashboardMovements) {
            if (movement.isIncome()) {
                dashboardBalance += movement.getAmount();
                dashboardIncome += movement.getAmount();
            } else {
                dashboardBalance -= movement.getAmount();
                dashboardOutcome += movement.getAmount();
            }
        }
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_dashboard, container, false);

        TextView tvDashboardBalance = (TextView) layout.findViewById(R.id.tvDashboardBalance);
        tvDashboardBalance.setText("$" + Double.toString(dashboardBalance));
        if (dashboardBalance < 0) {
            tvDashboardBalance.setTextColor(Color.parseColor("#FA5858"));
        }

        TextView tvDashboardIncome = (TextView) layout.findViewById(R.id.tvDashboardIncome);
        tvDashboardIncome.setText("$" + Double.toString(dashboardIncome));

        TextView tvDashboardOutcome = (TextView) layout.findViewById(R.id.tvDashboardOutcome);
        tvDashboardOutcome.setText("$" + Double.toString(dashboardOutcome));
        tvDashboardOutcome.setTextColor(Color.parseColor("#FA5858"));

        ListView lvDashvoardTransactions = (ListView) layout.findViewById(R.id
                .lvDashboardLastMovements);
        List<Movement> subMovements = dashboardMovements;
        if (dashboardMovements.size() > 10) {
            subMovements = dashboardMovements.subList(0, 10);
        }
        MovementListAdapter adapter = new MovementListAdapter(getActivity().getApplicationContext(),
                R.layout.movement_list_adapter, subMovements);
        lvDashvoardTransactions.setAdapter(adapter);
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
