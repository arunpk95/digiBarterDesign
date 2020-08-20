package com.example.digibarterdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digibarterdesign.R;
import com.example.digibarterdesign.productDetail;

public class homeRecycleAdapter extends RecyclerView.Adapter<homeRecycleAdapter.homeHolder> {

    int images [];
    Context context;
    public homeRecycleAdapter(Context ct,int img[]){
        context = ct;
        images = img;
    }
    @NonNull
    @Override
    public homeRecycleAdapter.homeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_product_cell,parent,false);
        return new homeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull homeRecycleAdapter.homeHolder holder, int position) {
        holder.image.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class homeHolder extends RecyclerView.ViewHolder {
        ImageView image;
        public homeHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.productImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), productDetail.class);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
