package com.doe.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.doe.R;
import com.doe.ui.auth.LoginActivity;
import com.doe.ui.fragments.PublicationListsFragment;
import com.doe.ui.fragments.OrganizationsFragment;
import com.doe.ui.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private TextView userName;
    private SharedPreferences spLogin;

    @BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation) NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        // As we're using a Toolbar, we should retrieve it and set it to be our ActionBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(true);
        }

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        if (mNavigationView != null) {
            setupNavigationView(mNavigationView);
        }

        this.spLogin = PreferenceManager.getDefaultSharedPreferences(this);

        View headerView = null;
        if (mNavigationView != null) {
            headerView = mNavigationView.getHeaderView(0);
            this.userName = headerView.findViewById(R.id.txtName);
            this.userName.setText(spLogin.getString("loginEmail", null));
        }

        selectItem(0);
    }

    private void setupNavigationView(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_organizations:
                        selectItem(0);
                        return true;
                    case R.id.action_lists:
                        selectItem(1);
                        return true;
                    case R.id.action_settings:
                        selectItem(2);
                        return true;
                    case R.id.action_logout:
                        logout();
                    default:
                        return true;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.drawer_menu, menu);
        return true;
    }

    private void selectItem(int position) {
        Fragment fragment = null;
        String title = null;

        switch (position) {
            case 0:
                fragment = new OrganizationsFragment();
                title = "Instituições";
                break;
            case 1:
                fragment = new PublicationListsFragment();
                title = "Publicações";
                break;
            case 2:
                fragment = new SettingsFragment();
                title = "Configurações";
                break;
        }

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, fragment).commit();

        setTitle(title);
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void logout() {

        Intent backToLogin = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(backToLogin);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}
