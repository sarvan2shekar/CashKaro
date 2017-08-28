package com.testapp.sarvan.cashkaro.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.testapp.sarvan.cashkaro.CarouselPagerAdapter;
import com.testapp.sarvan.cashkaro.R;

public class HomeActivity extends AppCompatActivity {
    public final static int LOOPS = 1000;
    public static int count = 6; //ViewPager items size should be dynamically got from CashKaro Web services
    public static int FIRST_PAGE = 6;
    private final int backDuration = 2000;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 998;
    private final int MY_PERMISSIONS_REQUEST_LOCATION = 999;
    private final int REQUEST_PERMISSION_SETTING = 997;
    public ViewPager pager;
    protected DrawerLayout mDrawerLayout;
    protected ListView mDrawerList;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected CharSequence mDrawerTitle;
    protected CharSequence mTitle;
    protected String[] mMenuTitles;
    private CarouselPagerAdapter adapter;
    private boolean doubleBackToExitPressedOnce = false;
    private SharedPreferences permissionStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.home_title));
        }
        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);
        pager = (ViewPager) findViewById(R.id.dealsCarousel);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int pageMargin = ((metrics.widthPixels / 4) * 2);
        pager.setPageMargin(-pageMargin);
        adapter = new CarouselPagerAdapter(this, getSupportFragmentManager());
        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        pager.addOnPageChangeListener(adapter);
        pager.setCurrentItem(FIRST_PAGE);
        pager.setOffscreenPageLimit(3);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mTitle = mDrawerTitle = getSupportActionBar().getTitle();
        mMenuTitles = getResources().getStringArray(R.array.menuVals);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.leftMenu);
        mDrawerList = (ListView) findViewById(R.id.navList);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(android.R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mMenuTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                //R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please tap Back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, backDuration);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    public void getPermission(String permissionName, final String permission, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle("Need " + permissionName + " Permission.");
            builder.setMessage("This app needs" + permissionName + " permission!");
            builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    ActivityCompat.requestPermissions(HomeActivity.this,
                            new String[]{permission}, requestCode);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    Toast.makeText(HomeActivity.this, "Permission denied :(", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        } else if (permissionStatus.getBoolean(permission, false)) {
            //Previously Permission Request was cancelled with 'Dont Ask Again',
            // Redirect to Settings after showing Information about why you need the permission
            Toast.makeText(getBaseContext(), "Go to Settings to Grant Permission",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent();
            i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            i.addCategory(Intent.CATEGORY_DEFAULT);
            i.setData(Uri.parse("package:" + getPackageName()));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(i);
        } else {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{permission},
                    requestCode);
            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permission, true);
            editor.apply();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission denied :(", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Permission denied :(", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    public class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            String selectedVal = (adapterView.getItemAtPosition(position)).toString();
            if (selectedVal.trim().equalsIgnoreCase(getString(R.string.camera))) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    getPermission("Camera", Manifest.permission.CAMERA, MY_PERMISSIONS_REQUEST_CAMERA);
                } else {
                    Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                }
            } else if (selectedVal.trim().equalsIgnoreCase(getString(R.string.location))) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    getPermission("Location", Manifest.permission.ACCESS_COARSE_LOCATION,
                            MY_PERMISSIONS_REQUEST_LOCATION);
                } else {
                    Snackbar.make(view, "Permission already granted.", Snackbar.LENGTH_LONG).show();
                }
            }
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }
}