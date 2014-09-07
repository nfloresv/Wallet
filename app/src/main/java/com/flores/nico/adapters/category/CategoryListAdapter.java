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
 * Created by nicoflores on 07-09-14.
 */
public class CategoryListAdapter extends ArrayAdapter<Category> {
    private Context context;
    private int resource;
    private List<Category> categories;


    public CategoryListAdapter (Context context, int resource, List<Category> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.categories = objects;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CategoryHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                    .LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(resource, parent, false);

            TextView categoryName = (TextView) row.findViewById(R.id.categoryListLayoutName);
            TextView categoryDescription = (TextView) row.findViewById(R.id
                    .categoryListLayoutDescription);

            holder = new CategoryHolder(categoryName, categoryDescription);
            row.setTag(holder);
        } else {
            holder = (CategoryHolder) row.getTag();
        }

        Category category = categories.get(position);
        holder.setNameText(category.getName());
        holder.setDescriptionText(category.getDescription());

        return row;
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
    }
}
