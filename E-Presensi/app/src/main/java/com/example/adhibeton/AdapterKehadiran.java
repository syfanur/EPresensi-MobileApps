package com.example.adhibeton;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterKehadiran extends RecyclerView.Adapter<AdapterKehadiran.MyViewHolder> {
    private Context mContext;
    private List<ModelAbsenKehadiran> mData;

    public AdapterKehadiran(Context mContext, List<ModelAbsenKehadiran> mData){
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public AdapterKehadiran.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.row_kehadiran,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKehadiran.MyViewHolder holder, int position) {
    holder.mStatus.setText(mData.get(position).getStatusDatang());
    holder.mTanggal.setText(mData.get(position).getTanggal());
    holder.mAbsen_datang.setText(mData.get(position).getWaktuDatang());
    holder.mAbsen_pulang.setText(mData.get(position).getWaktuPulang());
    holder.mDetail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mContext.startActivity(new Intent(mContext,DetailKehadiran.class));
        }
    });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Button mDetail;
        TextView mTanggal, mStatus, mAbsen_datang, mAbsen_pulang;
        private View mView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mView=itemView;
            mDetail=mView.findViewById(R.id.btn_detail);
            mStatus=mView.findViewById(R.id.rStatus);
            mTanggal=mView.findViewById(R.id.rTanggal);
            mAbsen_datang=mView.findViewById(R.id.rAbsen_datang);
            mAbsen_pulang=mView.findViewById(R.id.rAbsen_pulang);
        }
    }
}
