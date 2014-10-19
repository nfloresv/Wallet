package com.flores.nico.wallet;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flores.nico.utils.Credentials;
import com.flores.nico.utils.Initializer;


public class HomeActivity extends Activity {
    public static final int LOGIN_ACTIVITY_REQUEST_CODE = 0x01;
    private static final String FRAGMENT_CODE = "com.flores.nico.wallet.ACTUAL_FRAGMENT";
    private Fragment actualFragment;
    private int fragmentId;
    private ListView mDrawerList;
    private Credentials credentials;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        credentials = new Credentials(getApplicationContext());

        String[] sectionsList = getResources().getStringArray(R.array.drawer_section_array);
        if (credentials.isUserLoggedIn()) {
            String user_email = credentials.getUserEmail();
            String[] tmp = new String[sectionsList.length + 1];
            tmp[0] = user_email;
            System.arraycopy(sectionsList, 0, tmp, 1, sectionsList.length);
            sectionsList = tmp;
        }
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, sectionsList));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt(FRAGMENT_CODE, 1);
            selectItem(position);
        } else {
            selectItem(1);
        }

        final ActionBar actionBar = getActionBar();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed (View drawerView) {
                super.onDrawerClosed(drawerView);
                if (actionBar != null) {
                    actionBar.setTitle(mTitle);
                }
            }

            @Override
            public void onDrawerOpened (View drawerView) {
                super.onDrawerOpened(drawerView);
                if (actionBar != null) {
                    actionBar.setTitle(mDrawerTitle);
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        if (!credentials.isUserLoggedIn()) {
            //Only pre load the database if the user is not logged in
            Initializer initializer = new Initializer(getApplicationContext(),
                    getPreferences(Context.MODE_PRIVATE));
            initializer.load_categories();

            //Start user login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            boolean result = credentials.setLogout();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST_CODE);
            return result;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String emailText = data.getStringExtra(LoginActivity.USER_EMAIL);
                String passwordText = data.getStringExtra(LoginActivity.USER_PASSWORD);
                String firstNameText = data.getStringExtra(SignInActivity.USER_FIRST_NAME);
                String lastNameText = data.getStringExtra(SignInActivity.USER_LAST_NAME);

                String[] sectionsList = getResources().getStringArray(R.array.drawer_section_array);
                String[] tmp = new String[sectionsList.length + 1];
                tmp[0] = emailText;
                System.arraycopy(sectionsList, 0, tmp, 1, sectionsList.length);
                mDrawerList = (ListView) findViewById(R.id.left_drawer);
                mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, tmp));

                credentials.setLoginData(emailText, passwordText, firstNameText, lastNameText);
            }
        }
    }

    @Override
    protected void onPostCreate (Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_CODE, fragmentId);
    }

    private void selectItem (int position) {
        if (position == 1) {
            actualFragment = new MovementFragment();
            fragmentId = position;
            mTitle = getString(R.string.title_movement_fragment);
        } else if (position == 2) {
            actualFragment = new AllMovementsFragment();
            fragmentId = position;
            mTitle = getString(R.string.title_all_movements_fragment);
        } else if (position == 3) {
            actualFragment = new DashboardFragment();
            fragmentId = position;
            mTitle = getString(R.string.title_dashboard_fragment);
        } else if (position == 4) {
            actualFragment = new CategoryFragment();
            fragmentId = position;
            mTitle = getString(R.string.title_category_fragment);
        } else if (position == 5) {
            actualFragment = new DebtFragment();
            fragmentId = position;
            mTitle = getString(R.string.title_debt_fragment);
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, actualFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick (AdapterView parent, View view, int position, long id) {
            selectItem(position);
            mDrawerList.setItemChecked(position, true);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }
}
