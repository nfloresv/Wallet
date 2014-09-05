package com.flores.nico.adapters.movement;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flores.nico.database.Movement;
import com.flores.nico.wallet.R;

import java.util.List;

/**
 * Created by nicoflores on 05-09-14.
 */
public class MovementAdapter extends ArrayAdapter<Movement> {
    private Context context;
    private int layoutResource;
    private List<Movement> movements;

    public MovementAdapter (Context context, int resource, List<Movement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.movements = objects;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MovementHolder holder;

        if (row != null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResource, parent, false);

            TextView movementDate = (TextView) row.findViewById(R.id.movementLayoutDate);
            TextView movementAmount = (TextView) row.findViewById(R.id.movementLayoutAmount);
            TextView movementCategory = (TextView) row.findViewById(R.id.movementLayoutCategory);

            holder = new MovementHolder(movementDate, movementAmount, movementCategory);
            row.setTag(holder);
        } else {
            holder = (MovementHolder) row.getTag();
        }

        Movement movement = movements.get(position);
        Log.d("Movement", movement.toString());
        holder.setDateText(movement.getMovement_date().toString());
        holder.setAmountText("$" + Double.toString(movement.getAmount()));
        holder.setCategoryText(movement.getCategory().getName());

        return row;
    }

    private static class MovementHolder {
        private TextView movementDate;
        private TextView movementAmount;
        private TextView movementCategory;

        public MovementHolder (TextView movementDate, TextView movementAmount, TextView movementCategory) {
            this.movementDate = movementDate;
            this.movementAmount = movementAmount;
            this.movementCategory = movementCategory;
        }

        public void setDateText (String date) {
            movementDate.setText(date);
        }

        public void setAmountText (String amount) {
            movementAmount.setText(amount);
        }

        public void setCategoryText (String category) {
            movementCategory.setText(category);
        }
    }
}
