package com.testapp.sarvan.cashkaro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.testapp.sarvan.cashkaro.model.Coupons;
import com.testapp.sarvan.cashkaro.view.CarouselLinearLayout;
import com.testapp.sarvan.cashkaro.view.DealStorePageActivity;
import com.testapp.sarvan.cashkaro.view.HomeActivity;

import java.util.ArrayList;

/**
 * Created by sarva on 22-08-2017.
 */

public class ItemFragment extends Fragment {

    private static final String POSITON = "position";
    private static final String SCALE = "scale";
    //private static final String DRAWABLE_RESOURE = "resource";
    private static final String NAME_RESOURE = "name";

    private int screenWidth, screenHeight;
    private Coupons couponFlipkart1, couponFlipkart2, couponFlipkart3, couponAmazon1, couponAmazon2,
            couponJabong1, couponJabong2, couponNykaa1, couponNykaa2, couponMyntra1,
            couponMyntra2, couponShopclues1, couponShopclues2;
    private ArrayList<Coupons> couponsArrayList;

    private int[] imageArray = new int[]{R.drawable.flipkart, R.drawable.amazon,
            R.drawable.jabong, R.drawable.nykaa, R.drawable.myntra,
            R.drawable.shopclues};
    private String[] dealArray = new String[]{
            "flipkart", "amazon", "jabong", "nykaa", "myntra", "shopclues"
    };

    public static Fragment newInstance(HomeActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt(POSITON, pos);
        b.putFloat(SCALE, scale);

        return Fragment.instantiate(context, ItemFragment.class.getName(), b);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWidthAndHeight();
    }

    @Override
    public void onResume() {
        super.onResume();
        couponFlipkart1 = new Coupons(R.drawable.flipkart, getString(R.string.flipkart), "250 Rs.", "40%", "30th Aug, 2017", getString(R.string.url_flipkart));
        couponFlipkart2 = new Coupons(R.drawable.flipkart, getString(R.string.flipkart), "100 Rs.", "20%", "30th Sep, 2017", getString(R.string.url_flipkart));
        couponFlipkart3 = new Coupons(R.drawable.flipkart, getString(R.string.flipkart), "50 Rs.", "10%", "20th Sep, 2017", getString(R.string.url_flipkart));

        couponAmazon1 = new Coupons(R.drawable.amazon, getString(R.string.amazon), "50 Rs.", "10%", "15th Sep, 2017", getString(R.string.url_amazon));
        couponAmazon2 = new Coupons(R.drawable.amazon, getString(R.string.amazon), "2500 Rs.", "60%", "30th Oct, 2017", getString(R.string.url_amazon));

        couponJabong1 = new Coupons(R.drawable.jabong, getString(R.string.jabong), "999 Rs.", "30%", "30th Nov, 2017", getString(R.string.url_jabong));
        couponJabong2 = new Coupons(R.drawable.jabong, getString(R.string.jabong), "499 Rs.", "25%", "30th Jan, 2018", getString(R.string.url_jabong));

        couponNykaa1 = new Coupons(R.drawable.nykaa, getString(R.string.nykaa), "1000 Rs.", "13%", "31st Aug, 2017", getString(R.string.url_nykaa));
        couponNykaa2 = new Coupons(R.drawable.nykaa, getString(R.string.nykaa), "2500 Rs.", "20%", "2nd Sep, 2017", getString(R.string.url_nykaa));

        couponMyntra1 = new Coupons(R.drawable.myntra, getString(R.string.myntra), "500 Rs.", "60%", "4th Dec, 2017", getString(R.string.url_myntra));
        couponMyntra2 = new Coupons(R.drawable.myntra, getString(R.string.myntra), "140 Rs.", "70%", "30th Jun, 2018", getString(R.string.url_myntra));

        couponShopclues1 = new Coupons(R.drawable.shopclues, getString(R.string.shopclues), "222 Rs.", "80%", "14th Oct, 2017", getString(R.string.url_shopclues));
        couponShopclues2 = new Coupons(R.drawable.shopclues, getString(R.string.shopclues), "111 Rs.", "16%", "10th Sep, 2017", getString(R.string.url_shopclues));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        final int postion = this.getArguments().getInt(POSITON);
        float scale = this.getArguments().getFloat(SCALE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, screenHeight / 2);
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_deal, container, false);

        CarouselLinearLayout root = linearLayout.findViewById(R.id.root_container);
        ImageView imageView = linearLayout.findViewById(R.id.pagerImg);

        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(imageArray[postion]);

        //handling click event
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DealStorePageActivity.class);
                intent.putExtra(NAME_RESOURE, dealArray[postion]);
                couponsArrayList = new ArrayList<Coupons>();
                switch (dealArray[postion]) {
                    case "flipkart":
                        couponsArrayList.add(couponFlipkart1);
                        couponsArrayList.add(couponFlipkart2);
                        couponsArrayList.add(couponFlipkart3);
                        break;
                    case "amazon":
                        couponsArrayList.add(couponAmazon1);
                        couponsArrayList.add(couponAmazon2);
                        break;
                    case "jabong":
                        couponsArrayList.add(couponJabong1);
                        couponsArrayList.add(couponJabong2);
                        break;
                    case "nykaa":
                        couponsArrayList.add(couponNykaa1);
                        couponsArrayList.add(couponNykaa2);
                        break;
                    case "myntra":
                        couponsArrayList.add(couponMyntra1);
                        couponsArrayList.add(couponMyntra2);
                        break;
                    case "shopclues":
                        couponsArrayList.add(couponShopclues1);
                        couponsArrayList.add(couponShopclues2);
                        break;
                }
                intent.putParcelableArrayListExtra(dealArray[postion], couponsArrayList);

                startActivity(intent);
            }
        });

        root.setScaleBoth(scale);

        return linearLayout;
    }

    /**
     * Get device screen width and height
     */
    private void getWidthAndHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;
    }
}
