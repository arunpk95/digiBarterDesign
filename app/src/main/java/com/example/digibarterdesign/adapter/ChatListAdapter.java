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

import com.example.digibarterdesign.R;
import com.example.digibarterdesign.productDetail;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.homeHolder> {

    int images [];
    String names[],messages[];

    Context context;
    public ChatListAdapter(Context ct, int img[], String name[], String message[]){
        context = ct;
        images = img;
        names = name;
        messages = message;
    }
    @NonNull
    @Override
    public ChatListAdapter.homeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chatview_list_item,parent,false);
        return new homeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.homeHolder holder, int position) {
        holder.image.setImageResource(images[position]);
        holder.message.setText(messages[position]);
        holder.name.setText(names[position]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class homeHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView message, name;
        public homeHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            message = itemView.findViewById(R.id.message);
            name = itemView.findViewById(R.id.profile_name);
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
