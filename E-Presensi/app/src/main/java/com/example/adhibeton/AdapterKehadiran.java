package com.example.adhibeton;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public void onBindViewHolder(@NonNull final AdapterKehadiran.MyViewHolder holder, final int position) {

    holder.mStatus.setText(mData.get(position).getStatusDatang());
    holder.mTanggal.setText(mData.get(position).getTanggal());
    holder.mAbsen_datang.setText(mData.get(position).getWaktuDatang());
    holder.mAbsen_pulang.setText(mData.get(position).getWaktuPulang());
//        Picasso.get().load(mData.get(position).getImageDatang()).into(holder.mImageDatang);
//        Picasso.get().load(mData.get(position).getImagePulang()).into(holder.mImagePulang);




        holder.mDetail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String rStatus=holder.mStatus.getText().toString();
            String rTanggal=holder.mTanggal.getText().toString();
            String rDatang=holder.mAbsen_datang.getText().toString();
            String rPulang=holder.mAbsen_pulang.getText().toString();
            String rStatusPulang=mData.get(position).getStatusPulang();
//            Drawable drawable = holder.mImageDatang.getDrawable();
//            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();



            Intent intent=new Intent(mContext,DetailKehadiran.class);
            intent.putExtra("id", mData.get(position).getImageDatang());
            intent.putExtra("idPulang", mData.get(position).getImagePulang());
            intent.putExtra("status",rStatus);
            intent.putExtra("tanggal",rTanggal);
            intent.putExtra("datang",rDatang);
            intent.putExtra("pulang",rPulang);
            intent.putExtra("statuspulang",rStatusPulang);
            mContext.startActivity(intent);
        }
    });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Button mDetail;
        TextView mTanggal, mStatus, mAbsen_datang, mAbsen_pulang,mStatusPulang;
        ImageView img, mImageDatang, mImagePulang;
        private View mView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mView=itemView;
            mDetail=mView.findViewById(R.id.btn_detail);
            mStatus=mView.findViewById(R.id.rStatus);
            mTanggal=mView.findViewById(R.id.rTanggal);
            mAbsen_datang=mView.findViewById(R.id.rAbsen_datang);
            mAbsen_pulang=mView.findViewById(R.id.rAbsen_pulang);
            img=mView.findViewById(R.id.calendar);
            mStatusPulang=mView.findViewById(R.id.rStatusPulang);
//            mImageDatang=mView.findViewById(R.id.ImageDatang);
//            mImagePulang=mView.findViewById(R.id.ImagePulang);
        }
    }
}
