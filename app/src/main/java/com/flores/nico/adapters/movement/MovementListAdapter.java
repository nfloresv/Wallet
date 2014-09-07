package com.flores.nico.adapters.movement;

import android.content.Context;
import android.graphics.Color;
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
public class MovementListAdapter extends ArrayAdapter<Movement> {
    private Context context;
    private int layoutResource;
    private List<Movement> movements;

    public MovementListAdapter (Context context, int resource, List<Movement> objects) {
        super(context, resource, objects);
        this.context = context;
        this.layoutResource = resource;
        this.movements = objects;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View row = convertView;
        MovementHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResource, parent, false);

            TextView movementDate = (TextView) row.findViewById(R.id.movementListLayoutDate);
            TextView movementAmount = (TextView) row.findViewById(R.id.movementListLayoutAmount);
            TextView movementCategory = (TextView) row.findViewById(R.id.movementListLayoutCategory);
            TextView movementDescription = (TextView) row.findViewById(R.id
                    .movementListLayoutDescription);

            holder = new MovementHolder(movementDate, movementAmount, movementCategory, movementDescription);
            row.setTag(holder);
        } else {
            holder = (MovementHolder) row.getTag();
        }

        Movement movement = movements.get(position);
        String date = String.format("%1$ta %1$td %1$tb %1$tY", movement.getMovement_date());/*"%1$tF"*/
        holder.setDateText(date);
        holder.setAmountText("$" + Double.toString(movement.getAmount()));
        holder.setCategoryText(movement.getCategory().getName());
        holder.setDescriptionText(movement.getDescription());
        holder.setIncome(movement.isIncome());

        return row;
    }

    private static class MovementHolder {
        private TextView movementDate;
        private TextView movementAmount;
        private TextView movementCategory;
        private TextView movementDescription;

        public MovementHolder (TextView movementDate, TextView movementAmount,
                               TextView movementCategory, TextView movementDescription) {
            this.movementDate = movementDate;
            this.movementAmount = movementAmount;
            this.movementCategory = movementCategory;
            this.movementDescription = movementDescription;
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

        public void setDescriptionText (String description) {
            movementDescription.setText(description);
        }

        public void setIncome(Boolean income) {
            if (!income) {
                movementAmount.setTextColor(Color.parseColor("#FA5858"));
            } else {
                movementAmount.setTextColor(Color.parseColor("#BDBDBD"));
            }
        }
    }
}
