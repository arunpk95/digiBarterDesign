package com.example.digibarterdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.digibarterdesign.R;
import com.example.digibarterdesign.SliderItem;
import com.example.digibarterdesign.productDetail;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreHolder> {
    List<SliderItem> sliderItem;
    Context context;
    ViewPager2 viewPager2;
    public StoreAdapter(Context ct,List<SliderItem> sliderItem,ViewPager2 viewPager2){
        this.context = ct;
        this.sliderItem = sliderItem;
        this.viewPager2 = viewPager2;
    }
    @NonNull

    public StoreAdapter.StoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_pager_container,parent,false);
        return new StoreAdapter.StoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.StoreHolder holder, int position) {
        holder.setImage(sliderItem.get(position));
    }

    @Override
    public int getItemCount() {
        return sliderItem.size();
    }

    public class StoreHolder extends RecyclerView.ViewHolder {
        RoundedImageView imageView;
        public StoreHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlide);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), productDetail.class);
                    v.getContext().startActivity(intent);
                }
            });

        }

        void setImage(SliderItem sliderItem){
            imageView.setImageResource(sliderItem.getImage());
        }
    }
}
