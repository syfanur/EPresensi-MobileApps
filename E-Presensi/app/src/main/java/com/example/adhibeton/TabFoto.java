package com.example.adhibeton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class TabFoto extends Fragment {
TextView mTanggal;
ImageView absenDatang, absenPulang;
    public TabFoto() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.activity_tab_foto, container, false);

        mTanggal = v.findViewById(R.id.tanggalFoto);
        absenDatang=v.findViewById(R.id.gambarDatang);
        absenPulang=v.findViewById(R.id.gambarPulang);


        String rTanggalFoto = getActivity().getIntent().getStringExtra("tanggal");
        String image = getActivity().getIntent().getStringExtra("id");
        String imagePulang = getActivity().getIntent().getStringExtra("idPulang");
        mTanggal.setText(rTanggalFoto);
        Picasso.get().load(image).into(absenDatang);
        Picasso.get().load(imagePulang).into(absenPulang);
        return v;
    }
}