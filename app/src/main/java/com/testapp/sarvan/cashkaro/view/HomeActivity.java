package com.testapp.sarvan.cashkaro.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.testapp.sarvan.cashkaro.CarouselPagerAdapter;
import com.testapp.sarvan.cashkaro.R;

public class HomeActivity extends AppCompatActivity {

    public final static int LOOPS = 1000;
    public static int count = 6; //ViewPager items size should be dynamically got from CashKaro Web services
    public static int FIRST_PAGE = 6;
    private final int backDuration = 2000;
    public ViewPager pager;
    private CarouselPagerAdapter adapter;
    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.home_title));
        }
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
}