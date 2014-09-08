package com.flores.nico.wallet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
    private EditText categoryName;
    private EditText categoryDescription;
    private List<Category> categories;

    /*private OnFragmentInteractionListener mListener;*/

    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = Category.listAll(Category.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_category, container, false);

        categoryName = (EditText) layout.findViewById(R.id.categoryFragmentInputName);
        categoryDescription = (EditText) layout.findViewById(R.id.categoryFragmentInputDescription);

        ListView categoriesLv = (ListView) layout.findViewById(R.id.categoryFragmentLvCategories);
        CategoryListAdapter adapter = new CategoryListAdapter(getActivity().getApplicationContext(),
                R.layout.category_list_adapter, categories);
        categoriesLv.setAdapter(adapter);

        Button saveButton = (Button) layout.findViewById(R.id.categoryFragmentBtnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                saveCategory(view);
            }
        });
        return layout;
    }

    public void saveCategory(View view) {
        Toast toast;
        String message;
        int duration = Toast.LENGTH_SHORT;

        String name = categoryName.getText().toString();
        String description = categoryDescription.getText().toString();
        if (!name.isEmpty()) {
            Category category = new Category(name, description);
            category.save();

            message = getString(R.string.toast_category_fragment_category_saved);
            toast = Toast.makeText(getActivity().getApplicationContext(), message, duration);

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new CategoryFragment())
                    .commit();
        } else {
            message = getString(R.string.toast_category_fragment_category_error);
            toast = Toast.makeText(getActivity().getApplicationContext(), message, duration);
        }
        toast.show();
    }

    /*// TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    /*public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }*/

}
