package com.testapp.sarvan.cashkaro.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.testapp.sarvan.cashkaro.MyAdapter;
import com.testapp.sarvan.cashkaro.R;
import com.testapp.sarvan.cashkaro.model.Coupons;

import java.util.ArrayList;

/**
 * Created by sarva on 22-08-2017.
 */

public class DealStorePageActivity extends HomeActivity {
    private static final String NAME_RESOURE = "name";
    ArrayList<Coupons> coupons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_deal_store_page);
        String name = getIntent().getExtras().getString(NAME_RESOURE);
        coupons = getIntent().getParcelableArrayListExtra(name);
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        MyAdapter myAdapter = new MyAdapter(coupons);
        rv.setAdapter(myAdapter);
        if (getSupportActionBar() != null && coupons != null && coupons.get(0) != null) {
            getSupportActionBar().setTitle(coupons.get(0).getName());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
