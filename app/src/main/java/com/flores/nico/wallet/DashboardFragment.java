package com.flores.nico.wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

        TextView tvDashboardBalance = (TextView) layout.findViewById(R.id.dashboardFragmentTvBalance);
        tvDashboardBalance.setText("$" + Double.toString(dashboardBalance));
        if (dashboardBalance < 0) {
            tvDashboardBalance.setTextColor(Color.parseColor("#FA5858"));
        }

        TextView tvDashboardIncome = (TextView) layout.findViewById(R.id.dashboardFragmentTvIncome);
        tvDashboardIncome.setText("$" + Double.toString(dashboardIncome));

        TextView tvDashboardOutcome = (TextView) layout.findViewById(R.id.dashboardFragmentTvOutcome);
        tvDashboardOutcome.setText("$" + Double.toString(dashboardOutcome));
        tvDashboardOutcome.setTextColor(Color.parseColor("#FA5858"));

        ListView lvDashvoardTransactions = (ListView) layout.findViewById(R.id
                .dashboardFragmentLvLastMovements);
        List<Movement> subMovements = dashboardMovements;
        if (dashboardMovements.size() > 10) {
            subMovements = dashboardMovements.subList(0, 10);
        }
        MovementListAdapter adapter = new MovementListAdapter(getActivity().getApplicationContext(),
                R.layout.movement_list_adapter, subMovements);
        lvDashvoardTransactions.setAdapter(adapter);
        lvDashvoardTransactions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                movementClick(i);
            }
        });
        return layout;
    }

    public void movementClick (int position) {
        Movement movement = dashboardMovements.get(position);

        Context context = getActivity().getApplicationContext();

        Intent intent = new Intent(context, ViewMovementActivity.class);
        intent.putExtra(ViewMovementActivity.MOVEMENT_ID, movement.getId());

        startActivity(intent);
    }

}
