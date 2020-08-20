package com.example.digibarterdesign;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.digibarterdesign.adapter.ChatListAdapter;
import com.example.digibarterdesign.adapter.homeRecycleAdapter;
import com.example.digibarterdesign.adapter.notificationRecycleAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {
    RecyclerView recycleView;
    Toolbar searchToolBar;
    SearchView searchView;

    int images[]={R.drawable.a,R.drawable.aa,R.drawable.ab,R.drawable.b,R.drawable.ac};
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        searchToolBar = (Toolbar) findViewById(R.id.toolbar);
        //Search View setting Color
        /*searchView = findViewById(R.id.searchView);
        int id =  searchView.getContext().getResources().getIdentifier("android:id/searchView", null, null);
        TextView textView = (TextView) searchView.findViewById(id);
        textView.setTextColor(000000);
        textView.setHintTextColor(000000);*/

        recycleView = findViewById(R.id.recycleView);
        homeRecycleAdapter homeAdapter = new homeRecycleAdapter(this,images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.setAdapter(homeAdapter);

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateHomeRecycler();
            }
        });
        findViewById(R.id.notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotificationRecycler();
            }
        });
        findViewById(R.id.addProduct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddProduct.class));
            }
        });
        findViewById(R.id.chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateChatRecycler();
            }
        });
        findViewById(R.id.store).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                updateStoreRecycler();
            }
        });

        findViewById(R.id.profilePage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddProduct.class));
            }
        });
        findViewById(R.id.filterPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),filterPage.class));
            }
        });

    }

    private void updateHomeRecycler() {
        recycleView.removeAllViews();
        homeRecycleAdapter homeAdapter = new homeRecycleAdapter(this,images);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.setAdapter(homeAdapter);
    }
    private void updateNotificationRecycler() {
        recycleView.removeAllViews();
        notificationRecycleAdapter notificationRecycleAdapter = new notificationRecycleAdapter(this,new String[]{"Arun","Sri Ram","Sathya"},new String[]{"Looking for Exchanging shoes","Exchanging headphones","Swap Speakers"});
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.setAdapter(notificationRecycleAdapter);
    }
    private void updateChatRecycler() {
        recycleView.removeAllViews();
        ChatListAdapter chatListAdapter = new ChatListAdapter(this,new int[]{R.drawable.a,R.drawable.aa,R.drawable.ab},new String[]{"Sandra","Arun","Sam"},new String[]{"Hey, Is this still available","See you tommowor","Thank You!!"});
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.setAdapter(chatListAdapter);
    }
    private void updateStoreRecycler() {
        recycleView.removeAllViews();

        startActivity(new Intent(getApplicationContext(),storePage.class));
        /*ChatListAdapter chatListAdapter = new ChatListAdapter(this,new int[]{R.drawable.a,R.drawable.aa,R.drawable.ab},new String[]{"Sandra","Arun","Sam"},new String[]{"Hey, Is this still available","See you tommowor","Thank You!!"});
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1,GridLayoutManager.VERTICAL,false);
        recycleView.setLayoutManager(gridLayoutManager);
        recycleView.setAdapter(chatListAdapter);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}