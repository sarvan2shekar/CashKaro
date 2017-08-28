package com.testapp.sarvan.cashkaro.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.testapp.sarvan.cashkaro.MyAdapter;
import com.testapp.sarvan.cashkaro.R;
import com.testapp.sarvan.cashkaro.model.Coupons;

import java.util.ArrayList;

/**
 * Created by sarva on 22-08-2017.
 */

public class DealStorePageActivity extends HomeActivity {
    private static final String NAME_RESOURE = "name";
    private static WebView webView;
    private static ProgressDialog dialog;
    ArrayList<Coupons> coupons;

    public static void openLink(String url) {
        webView.setVisibility(View.VISIBLE);
        dialog.show();
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        dialog.setMessage("Taking you to the store. Please wait.");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        webView.loadUrl(url);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deal_store_page);
        String name = getIntent().getExtras().getString(NAME_RESOURE);
        coupons = getIntent().getParcelableArrayListExtra(name);
        webView = (WebView) findViewById(R.id.webView);
        dialog = new ProgressDialog(DealStorePageActivity.this);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        MyAdapter myAdapter = new MyAdapter(coupons);
        rv.setAdapter(myAdapter);
        if (getSupportActionBar() != null && coupons != null && coupons.get(0) != null) {
            getSupportActionBar().setTitle(coupons.get(0).getName());
        }
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
    public void onBackPressed() {
        Intent intent = getIntent();
        finish();
        if (webView != null && webView.getVisibility() != View.GONE) {
            startActivity(intent);
        }
    }
}
