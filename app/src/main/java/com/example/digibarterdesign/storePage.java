package com.example.digibarterdesign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.digibarterdesign.adapter.StoreAdapter;

import java.util.ArrayList;
import java.util.List;

public class storePage extends AppCompatActivity {
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);
        viewPager2 = findViewById(R.id.viewPager);
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.a));
        sliderItems.add(new SliderItem(R.drawable.aa));
        sliderItems.add(new SliderItem(R.drawable.ab));
        sliderItems.add(new SliderItem(R.drawable.ac));
        sliderItems.add(new SliderItem(R.drawable.b));

        viewPager2.setAdapter(new StoreAdapter(this,sliderItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                page.setScaleY(0.85F + (1-Math.abs(position)) * 0.15f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
    }
}