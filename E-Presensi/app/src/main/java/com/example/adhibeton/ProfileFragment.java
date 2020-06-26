package com.example.adhibeton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class ProfileFragment extends Fragment {

    Button eData, ePassword, eLogout;


    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_profile, container, false);



        eData=v.findViewById(R.id.editData);
        eData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), EditData.class);
                startActivity(a);
            }
        });
        ePassword=v.findViewById(R.id.editPassword);
        ePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), EditPassword.class);
                startActivity(a);
            }
        });

        eLogout=v.findViewById(R.id.logout);
        eLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().destroy();
                Toast.makeText(getActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
                Intent home = new Intent(getActivity(), Login.class);
                startActivity(home);
            }
        });

        TextView nama = v.findViewById(R.id.nama);
        TextView posisi = v.findViewById(R.id.posisi);
        CircularImageView profil = v.findViewById(R.id.profil);

        nama.setText(Prevalent.currentOnlineUser.getNama());
        posisi.setText(Prevalent.currentOnlineUser.getPosisi());
        Picasso.get().load(Prevalent.currentOnlineUser.getProfil()).into(profil);

        return v;


    }
}