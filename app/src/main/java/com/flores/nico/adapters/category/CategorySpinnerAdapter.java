package com.flores.nico.adapters.category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.flores.nico.database.Category;
import com.flores.nico.wallet.R;

import java.util.List;

/**
 * Created by nicoflores on 09-09-14.
 */
public class CategorySpinnerAdapter extends ArrayAdapter<Category> {
    private Context context;
    private int resource;
    private List<Category> categories;

    public CategorySpinnerAdapter (Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.categories = objects;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        /*This method is used to return the customized view at specified position in list.*/
        View row = convertView;
        CategoryHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource, parent, false);

            TextView categoryName = (TextView) row.findViewById(R.id.categorySpinnerLayoutName);
            TextView categoryDescription = (TextView) row.findViewById(R.id
                    .categorySpinnerLayoutDescription);

            holder = new CategoryHolder(categoryName, categoryDescription);
            row.setTag(holder);
        } else {
            holder = (CategoryHolder) row.getTag();
        }

        Category category = categories.get(position);
        holder.setNameText(category.getName());
        holder.setVisibleDescription(false);

        return row;
    }

    @Override
    public View getDropDownView (int position, View convertView, ViewGroup parent) {
        /*This method is used to display the dropdown popup that contains data.*/
        View row = convertView;
        CategoryHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource, parent, false);

            TextView categoryName = (TextView) row.findViewById(R.id.categorySpinnerLayoutName);
            TextView categoryDescription = (TextView) row.findViewById(R.id
                    .categorySpinnerLayoutDescription);

            holder = new CategoryHolder(categoryName, categoryDescription);
            row.setTag(holder);
        } else {
            holder = (CategoryHolder) row.getTag();
        }

        Category category = categories.get(position);
        holder.setNameText(category.getName());
        holder.setDescriptionText(category.getDescription());
        holder.setVisibleDescription(true);

        return row;
    }

    @Override
    public int getPosition (Category item) {
        for (int i = 0; i < categories.size(); i++) {
            Category category = categories.get(i);
            if (category.getId() == item.getId()) {
                return i;
            }
        }
        return super.getPosition(item);
    }

    private static class CategoryHolder {
        private TextView categoryName;
        private TextView categoryDescription;

        private CategoryHolder (TextView categoryName, TextView categoryDescription) {
            this.categoryName = categoryName;
            this.categoryDescription = categoryDescription;
        }

        public void setNameText(String name) {
            categoryName.setText(name);
        }

        public void setDescriptionText(String description) {
            categoryDescription.setText(description);
        }

        public void setVisibleDescription (boolean visibility) {
            if (visibility) {
                categoryDescription.setVisibility(View.VISIBLE);
            } else {
                categoryDescription.setVisibility(View.GONE);
            }
        }
    }
}
