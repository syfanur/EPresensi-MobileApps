package com.example.adhibeton;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class IzinViewHolder extends RecyclerView.ViewHolder {
        TextView mjenis, mtanggal, mstatus, mstart, makhir, mket;
        ImageView mbukti;
        View v;

public IzinViewHolder(@NonNull View itemView) {
        super(itemView);
        mjenis = itemView.findViewById(R.id.jenisizin);
        mtanggal = itemView.findViewById(R.id.tanggal);
        mstatus = itemView.findViewById(R.id.status);
        mbukti = itemView.findViewById(R.id.buktiizin);
        mstart = itemView.findViewById(R.id.mtanggal);
        makhir = itemView.findViewById(R.id.makhir);
        mket = itemView.findViewById(R.id.keterangan);
        v = itemView;

        }}

