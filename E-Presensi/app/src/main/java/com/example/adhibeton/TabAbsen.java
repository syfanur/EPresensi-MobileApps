package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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

public class TabAbsen extends Fragment {

    TextView Datang, Pulang, StatusDatang, StatusPulang, Tanggal;

    public TabAbsen() {
        // Required empty public constructor
    }

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

    private void pulangdisplay(TextView pulang, TextView statusPulang) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp()).child("AbsenPulang").child("JULY").child("Fri, 3 Jul 2020");
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("waktu").exists())
                    {


                        String pulang = dataSnapshot.child("waktu").getValue().toString();
                        String statusPulang = dataSnapshot.child("status").getValue().toString();
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

    private void datangdisplay(TextView datang, TextView statusDatang, TextView tanggal) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp()).child("AbsenDatang").child("JULY").child("Fri, 3 Jul 2020");
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("waktu").exists())
                    {


                        String datang = dataSnapshot.child("waktu").getValue().toString();
                        String statusDatang = dataSnapshot.child("status").getValue().toString();
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
