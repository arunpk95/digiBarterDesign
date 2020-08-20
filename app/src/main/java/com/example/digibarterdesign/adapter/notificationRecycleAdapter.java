package com.example.digibarterdesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digibarterdesign.R;
import com.example.digibarterdesign.productDetail;

public class notificationRecycleAdapter extends RecyclerView.Adapter<notificationRecycleAdapter.homeHolder> {

    String msgs [];
    Context context;
    public notificationRecycleAdapter(Context ct, String name[], String itemTitle[]){
        context = ct;
        msgs = new String[name.length];
        for(int i=0;i<name.length;i++)
        {
            msgs[i] = name[i] + " wants to barter for '" + itemTitle[i] + "'";
        }
    }
    @NonNull
    @Override
    public notificationRecycleAdapter.homeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.notification_list_item,parent,false);
        return new homeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notificationRecycleAdapter.homeHolder holder, int position) {
        holder.textViewMsg.setText(msgs[position]);
    }

    @Override
    public int getItemCount() {
        return msgs.length;
    }

    public class homeHolder extends RecyclerView.ViewHolder {
        TextView textViewMsg;
        public homeHolder(@NonNull View itemView) {
            super(itemView);
            textViewMsg = itemView.findViewById(R.id.textViewMsg);
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
