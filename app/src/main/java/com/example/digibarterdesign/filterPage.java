package com.example.digibarterdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

public class filterPage extends AppCompatActivity {
    ChipGroup sortChipGroup;
    ChipGroup timeChipGroup;
    SeekBar distanceSlider;
    TextView distanceCount;
    Spinner category;
    String[] country = { "All Categories", "Electronics", "Car", "Gaming", "Others"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_page);
        sortChipGroup = findViewById(R.id.sortChipGroup);
        timeChipGroup = findViewById(R.id.timeChipGroup);
        distanceSlider = findViewById(R.id.distanceSlider);
        distanceCount = findViewById(R.id.distanceCount);
        category = findViewById(R.id.category);

        ArrayAdapter categoryAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,country);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(categoryAdapter);

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        sortChipGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {

            }
        });

        distanceSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int pval = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int)Math.round(progress/25 ))*25;
                seekBar.setProgress(progress);
                distanceCount.setText(progress + " km");
                pval = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(pval == 1000){
                    distanceCount.setText("Default");
                }else{
                    distanceCount.setText(pval + " km");
                }
            }
        });
    }
}