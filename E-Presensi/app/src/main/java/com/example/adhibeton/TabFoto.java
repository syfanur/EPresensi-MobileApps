package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TabFoto extends Fragment {

TextView mTanggal;
    public TabFoto() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.activity_tab_foto, container, false);

        mTanggal = v.findViewById(R.id.tanggalFoto);

        String rTanggalFoto = getActivity().getIntent().getStringExtra("tanggal");

        mTanggal.setText(rTanggalFoto);



        return v;
    }

}