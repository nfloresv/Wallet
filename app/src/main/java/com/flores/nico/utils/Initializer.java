package com.flores.nico.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.flores.nico.database.Category;
import com.flores.nico.wallet.R;

/**
 * Created by nicoflores on 03-09-14.
 */
public class Initializer {
    private Context context;
    private SharedPreferences sharedPrefs;

    public Initializer (Context context, SharedPreferences sharedPrefs) {
        this.context = context;
        this.sharedPrefs = sharedPrefs;
    }

    public void load_categories () {
        String no_categories = context.getString(R.string.code_no_categories_present);
        String categories_present = sharedPrefs.getString(context.getString(R.string
                .code_categories_present), no_categories);

        if (categories_present.equalsIgnoreCase(no_categories)) {
            //Set the categories present in the Shared Preferences
            SharedPreferences.Editor editor = sharedPrefs.edit();
            editor.putString(context.getString(R.string.code_categories_present), "Categories Present");
            editor.apply();

            //Save the categories to the database
            Category food = new Category(context.getString(R.string.category_food),
                    context.getString(R.string.category_food_description));
            food.save();
            Category entertainment = new Category(context.getString(R.string.category_entertainment),
                    context.getString(R.string.category_entertainment_description));
            entertainment.save();
            Category salary = new Category(context.getString(R.string.category_salary),
                    context.getString(R.string.category_salary_description));
            salary.save();
            Category transport = new Category(context.getString(R.string.category_transport),
                    context.getString(R.string.category_clothes_description));
            transport.save();
            Category service = new Category(context.getString(R.string.category_service),
                    context.getString(R.string.category_service_description));
            service.save();
            Category rent = new Category(context.getString(R.string.category_rent),
                    context.getString(R.string.category_clothes_description));
            rent.save();
            Category tax = new Category(context.getString(R.string.category_tax),
                    context.getString(R.string.category_tax_description));
            tax.save();
            Category clothes = new Category(context.getString(R.string.category_clothes),
                    context.getString(R.string.category_clothes_description));
            clothes.save();
            Category education = new Category(context.getString(R.string.category_education),
                    context.getString(R.string.category_education_description));
            education.save();
            Category other = new Category(context.getString(R.string.category_other),
                    context.getString(R.string.category_other_description));
            other.save();
        }
    }
}
