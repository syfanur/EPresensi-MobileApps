package com.example.adhibeton;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IzinViewHolder extends RecyclerView.ViewHolder {
        TextView mjenis, mtanggal, mstatus;
        ImageView mposter;
        View v;

public IzinViewHolder(@NonNull View itemView) {
        super(itemView);
        mjenis = itemView.findViewById(R.id.jenisizin);
        mtanggal = itemView.findViewById(R.id.tanggal);
        mstatus = itemView.findViewById(R.id.status);
        v = itemView;

        }}

