package com.TadpoleStudio.BantuAku;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<valueSetter> dataList;

    public RecyclerAdapter(Context mContext, ArrayList<valueSetter> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        //textView
        holder.textView.setText(dataList.get(position).getSkill());
        holder.textView2.setText(dataList.get(position).getSeller());

        //ImageView : Using Glide
        Glide.with(mContext)
                .load(dataList.get(position).getImageUrl())
                .into(holder.imageView);

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), detail_profile.class);

                String Image = dataList.get(position).getImageUrl();
                i.putExtra("Image", Image);

                String Skill = dataList.get(position).getSkill();
                i.putExtra("Skill", Skill);

                String sellerName = dataList.get(position).getSeller();
                i.putExtra("SellerName", sellerName);

                String desc = dataList.get(position).getDesc();
                i.putExtra("Desc", desc);

                String WA = dataList.get(position).getWA();
                i.putExtra("Whatsapp", WA);

                String Email = dataList.get(position).getEmail();
                i.putExtra("Email", Email);

                String Phone = dataList.get(position).getPhone();
                i.putExtra("Phone", Phone);

                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //widgets
        ImageView imageView;
        TextView textView;
        TextView textView2;
        View v;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            textView = itemView.findViewById(R.id.textViewy);
            textView2 = itemView.findViewById(R.id.SellerName);
            v = itemView;
        }
    }

}
