package com.example.adhibeton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabAbsen extends Fragment {
    TextView mTanggal, mDatang, mPulang, mStatus, mStatusPulang;

    TextView Datang, Pulang, StatusDatang, StatusPulang, Tanggal;

    public TabAbsen() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
      View v= inflater.inflate(R.layout.activity_tab_absen, container, false);
        mTanggal = v.findViewById(R.id.tanggal);
        mStatus = v.findViewById(R.id.statusdatang);
        mDatang=v.findViewById(R.id.absen_datang);
        mPulang=v.findViewById(R.id.absen_pulang);
        mStatusPulang=v.findViewById(R.id.statuspulang);


        String rStatus = getActivity().getIntent().getStringExtra("status");
        String rTanggal = getActivity().getIntent().getStringExtra("tanggal");
        String rDatang = getActivity().getIntent().getStringExtra("datang");
        String rPulang = getActivity().getIntent().getStringExtra("pulang");
        String rStatusPulang=getActivity().getIntent().getStringExtra("statuspulang");
        mTanggal.setText(rTanggal);
        mStatus.setText(rStatus);
        mDatang.setText(rDatang);
        mPulang.setText(rPulang);
        mStatusPulang.setText(rStatusPulang);

      return v;
    }

}
