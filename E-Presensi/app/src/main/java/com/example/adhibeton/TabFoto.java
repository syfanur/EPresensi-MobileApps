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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TabFoto extends Fragment {

    TextView Tanggal;
    public TabFoto() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_tab_foto, container, false);

        Tanggal = (TextView) v.findViewById(R.id.tanggal);

        datangdisplay(Tanggal);

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void datangdisplay(TextView tanggal) {
        DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        //GetBulan
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        String bln = String.valueOf(currentMonth);

        //GetTahun
        LocalDate thisyear = LocalDate.now();
        int currentYear = thisyear.getYear();
        String thn = String.valueOf(currentYear);


        //GetTanggal
        LocalDate todaay = LocalDate.now();
        String tgl = todaay.format(formatterdate);
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("tanggal").exists())
                    {



                        String tanggal = dataSnapshot.child("tanggal").getValue().toString();
                        Tanggal.setText(tanggal);


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}