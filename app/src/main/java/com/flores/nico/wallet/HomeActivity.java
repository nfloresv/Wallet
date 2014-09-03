package com.flores.nico.wallet;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flores.nico.utils.Credentials;


public class HomeActivity extends Activity {
    private Fragment actualFragment;
    private ListView mDrawerList;
    private Credentials credentials;

    public static final int LOGIN_CODE = 0x00001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        if (actualFragment == null) {
            actualFragment = new TransactionFragment();
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, actualFragment)
                .commit();

        if (!credentials.isUserLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_CODE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
        } else if (id == R.id.action_logout) {
            boolean result = credentials.setLogout();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, LOGIN_CODE);
            return result;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_CODE) {
            if (resultCode == RESULT_OK) {
                String emailText = data.getStringExtra(LoginActivity.USER_EMAIL);
                String passwordText = data.getStringExtra(LoginActivity.USER_PASSWORD);

                String[] sectionsList = getResources().getStringArray(R.array.drawer_section_array);
                String[] tmp = new String[sectionsList.length + 1];
                tmp[0] = emailText;
                System.arraycopy(sectionsList, 0, tmp, 1, sectionsList.length);
                mDrawerList = (ListView) findViewById(R.id.left_drawer);
                mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, tmp));

                credentials.setLoginData(emailText, passwordText);
            }
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
            mDrawerList.setItemChecked(position, true);
            DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            mDrawerLayout.closeDrawer(mDrawerList);
        }
    }

    private void selectItem (int position) {
        if (position == 1) {
            actualFragment = new TransactionFragment();
        } else if (position == 2) {
            actualFragment = new AllTransactionsFragment();
        } else if (position == 3) {
            actualFragment = new DashboardFragment();
        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, actualFragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                //.addToBackStack(null)
                .commit();
    }
}
