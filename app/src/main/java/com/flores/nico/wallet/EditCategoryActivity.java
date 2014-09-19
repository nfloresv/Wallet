package com.flores.nico.wallet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.flores.nico.database.Category;


public class EditCategoryActivity extends Activity {
    private long category_id;
    private EditText categoryName;
    private EditText categoryDescription;

    public static final String CATEGORY_ID = "com.flores.nico.wallet.CATEGORY_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        category_id = intent.getLongExtra(CATEGORY_ID, 0);
        Category category = Category.findById(Category.class, category_id);

        Button saveButton = (Button) findViewById(R.id.editCategoryActivityBtnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                saveCategory(view);
            }
        });

        categoryName = (EditText) findViewById(R.id.editCategoryActivityInputName);
        categoryDescription = (EditText) findViewById(R.id
                .editCategoryActivityInputDescription);

        if (category_id != 0 || category != null) {
            actionBar.setTitle(category.getName());

            categoryName.setText(category.getName());
            categoryDescription.setText(category.getDescription());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_category, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }


    public void saveCategory(View view) {
        Toast toast;
        String message;
        int duration = Toast.LENGTH_SHORT;

        String name = categoryName.getText().toString();
        String description = categoryDescription.getText().toString();
        if (!name.isEmpty()) {
            Category category;
            if (category_id != 0) {
                category = Category.findById(Category.class, category_id);
                category.setName(name);
                category.setDescription(description);
            } else {
                category = new Category(name, description);
            }
            category.save();

            message = getString(R.string.toast_category_fragment_category_saved);
            toast = Toast.makeText(getApplicationContext(), message, duration);

            finish();
        } else {
            message = getString(R.string.toast_category_fragment_category_error);
            toast = Toast.makeText(getApplicationContext(), message, duration);
        }
        toast.show();
    }
}
