package com.flores.nico.wallet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.flores.nico.database.Category;


public class ViewCategory extends Activity {
    private long category_id;
    public static final String CATEGORY_ID = "com.flores.nico.wallet.CATEGORY_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            category_id = savedInstanceState.getLong(CATEGORY_ID, 0);
            Log.i("Category_id", Long.toString(category_id));
            Log.i("saved instance", "Not null");
        } else {
            Intent intent = getIntent();
            category_id = intent.getLongExtra(CATEGORY_ID, 0);
            Log.i("Category_id", Long.toString(category_id));
        }

        Category category = Category.findById(Category.class, category_id);
        if (category_id != 0 || category != null) {
            actionBar.setTitle(category.getName());

            TextView categoryName = (TextView) findViewById(R.id.viewCategoryActivityCategoryName);
            TextView categoryDescription = (TextView) findViewById(R.id
                    .viewCategoryActivityCategoryDescription);
            categoryName.setText(category.getName());
            categoryDescription.setText(category.getDescription());
        } else {
            Log.i("Category_id", Long.toString(category_id));
            Log.i("category", "null");
            // Send error toast
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_view_category_edit_category) {
            Context context = getApplicationContext();

            Intent intent = new Intent(context, EditCategory.class);
            intent.putExtra(EditCategory.CATEGORY_ID, category_id);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CATEGORY_ID, category_id);
        Log.i("saved instance", "saved");
    }
}
