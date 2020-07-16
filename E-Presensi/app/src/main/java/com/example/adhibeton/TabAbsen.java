package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabAbsen extends Fragment {
    TextView mTanggal, mDatang, mPulang, mStatus;

    public TabAbsen() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
      View v= inflater.inflate(R.layout.activity_tab_absen, container, false);
        mTanggal = v.findViewById(R.id.tanggal);
        mStatus = v.findViewById(R.id.status);
        mDatang=v.findViewById(R.id.jam);
        mPulang=v.findViewById(R.id.jampulang);

        String rStatus = getActivity().getIntent().getStringExtra("status");
        String rTanggal = getActivity().getIntent().getStringExtra("tanggal");
        String rDatang = getActivity().getIntent().getStringExtra("datang");
        String rPulang = getActivity().getIntent().getStringExtra("pulang");

        mTanggal.setText(rTanggal);
        mStatus.setText(rStatus);
        mDatang.setText(rDatang);
        mPulang.setText(rPulang);

      return v;
    }

}
