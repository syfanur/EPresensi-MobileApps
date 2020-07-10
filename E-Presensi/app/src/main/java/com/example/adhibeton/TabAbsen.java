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

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TabAbsen extends Fragment {

    TextView Datang, Pulang, StatusDatang, StatusPulang, Tanggal;

    public TabAbsen() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        View v = inflater.inflate(R.layout.activity_tab_absen, container, false);
        Datang = (TextView) v.findViewById(R.id.absen_datang);
        Pulang = (TextView) v.findViewById(R.id.absen_pulang);
        StatusDatang = (TextView) v.findViewById(R.id.statusdatang);
        StatusPulang = (TextView) v.findViewById(R.id.statuspulang);
        Tanggal = (TextView) v.findViewById(R.id.tanggal);

        datangdisplay(Datang, StatusDatang, Tanggal);
        pulangdisplay(Pulang, StatusPulang);

        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void pulangdisplay(TextView pulang, TextView statusPulang) {
        DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
        //GetBulan
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        String bln = String.valueOf(currentMonth);

        //GetTanggal
        LocalDate todaay = LocalDate.now();
        String tgl = todaay.format(formatterdate);

        //GetTahun
        LocalDate thisyear = LocalDate.now();
        int currentYear = thisyear.getYear();
        String thn = String.valueOf(currentYear);


        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).child(tgl);
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("waktuPulang").exists())
                    {


                        String pulang = dataSnapshot.child("waktuPulang").getValue().toString();
                        String statusPulang = dataSnapshot.child("statusPulang").getValue().toString();
                        Pulang.setText(pulang);
                        StatusPulang.setText(statusPulang);


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void datangdisplay(TextView datang, TextView statusDatang, TextView tanggal) {

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
                    if (dataSnapshot.child("waktuDatang").exists())
                    {


                        String datang = dataSnapshot.child("waktuDatang").getValue().toString();
                        String statusDatang = dataSnapshot.child("statusDatang").getValue().toString();
                        String tanggal = dataSnapshot.child("tanggal").getValue().toString();
                        Datang.setText(datang);
                        StatusDatang.setText(statusDatang);
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
