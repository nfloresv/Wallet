package com.flores.nico.wallet;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flores.nico.adapters.category.CategoryListAdapter;
import com.flores.nico.database.Category;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CategoryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class CategoryFragment extends Fragment {
    private List<Category> categories;

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        categories = Category.listAll(Category.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_category, container, false);

        ListView categoriesLv = (ListView) layout.findViewById(R.id.categoryFragmentLvCategories);
        CategoryListAdapter adapter = new CategoryListAdapter(getActivity().getApplicationContext(),
                R.layout.category_list_adapter, categories);
        categoriesLv.setAdapter(adapter);
        categoriesLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
                categoryClick(i);
            }
        });
        return layout;
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.category, menu);
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_view_category_add_category) {
            Context context = getActivity().getApplicationContext();

            Intent intent = new Intent(context, EditCategory.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume () {
        super.onResume();
        categories = Category.listAll(Category.class);
    }

    public void categoryClick (int position) {
        Category category = categories.get(position);

        Context context = getActivity().getApplicationContext();

        Intent intent = new Intent(context, ViewCategory.class);
        intent.putExtra(ViewCategory.CATEGORY_ID, category.getId());

        startActivity(intent);
    }

}
