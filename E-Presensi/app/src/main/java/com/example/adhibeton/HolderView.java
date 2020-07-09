package com.example.adhibeton;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HolderView extends RecyclerView.ViewHolder {
Button mDetail;
TextView mTanggal, mStatus, mAbsen_datang, mAbsen_pulang;
    private View mView;
    public HolderView(@NonNull View itemView) {
        super(itemView);
        mView=itemView;
        mDetail=mView.findViewById(R.id.btn_detail);
        mStatus=mView.findViewById(R.id.rStatus);
        mTanggal=mView.findViewById(R.id.rTanggal);
        mAbsen_datang=mView.findViewById(R.id.rAbsen_datang);
        mAbsen_pulang=mView.findViewById(R.id.rAbsen_pulang);
    }
    public void setDetails(Context ctx, String absen, String keterangan, String lokasi, String status, String tanggal, String waktu,String bulan, String tahun){
      TextView  mStatus=mView.findViewById(R.id.rStatus);
      TextView  mTanggal=mView.findViewById(R.id.rTanggal);
      TextView mAbsen_datang=mView.findViewById(R.id.rAbsen_datang);
      TextView mAbsen_pulang=mView.findViewById(R.id.rAbsen_pulang);

      mStatus.setText(status);
      mTanggal.setText(tanggal);
      mAbsen_datang.setText(waktu);
    }
    public void setDetailsPulang(Context ctx, String absen, String keterangan, String lokasi, String status, String tanggal, String waktu,String bulan, String tahun) {
        TextView mAbsen_pulang=mView.findViewById(R.id.rAbsen_pulang);

        mAbsen_pulang.setText(waktu);
    }
}
