package com.flores.nico.wallet;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
 * {@link AllMovementsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllMovementsFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class AllMovementsFragment extends Fragment {
    private List<Movement> allMovements;
    private double allMovementsBalance;

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
        lvAllMovements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                movementClick(i);
            }
        });
        return layout;
    }

    public void movementClick (int position) {
        Movement movement = allMovements.get(position);

        Context context = getActivity().getApplicationContext();

        Intent intent = new Intent(context, ViewMovementActivity.class);
        intent.putExtra(ViewMovementActivity.MOVEMENT_ID, movement.getId());

        startActivity(intent);
    }
}
