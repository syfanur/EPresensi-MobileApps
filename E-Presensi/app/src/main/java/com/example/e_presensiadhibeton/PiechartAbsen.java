package com.example.e_presensiadhibeton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PiechartAbsen extends AppCompatActivity {

    long countTerlambat, countHadir, countTidakHadir;
    float jmlhTerlambat, jmlhHadir, jmlhTidakHadir;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart_absen);

        final PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(480,500);
        desc.setTextSize(14f);

        pieChart.setDescription(desc);

        //GetBulan
        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();
        String bln = String.valueOf(currentMonth);

        //GetIdUser
        //...

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefTerlambat = database.getReference()
                .child("Kehadiran")
                .child("NPP")
                .child("1202170038")
                .child("AbsenDatang")
                .child(bln);
        Query queryTerlambat = myRefTerlambat.orderByChild("status").equalTo("Terlambat");
        ValueEventListener eventListenerTerlambat = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countTerlambat = dataSnapshot.getChildrenCount();
                Log.d("TAG", String.valueOf(countTerlambat));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        queryTerlambat.addValueEventListener(eventListenerTerlambat);

        DatabaseReference myRefHadir = database.getReference()
                .child("Kehadiran")
                .child("NPP")
                .child("1202170038")
                .child("AbsenDatang")
                .child(bln);
        Query queryHadir = myRefHadir.orderByChild("status").equalTo("Hadir");
        ValueEventListener eventListenerHadir = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countHadir = dataSnapshot.getChildrenCount();
                Log.d("TAG", String.valueOf(countHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        queryHadir.addValueEventListener(eventListenerHadir);

        DatabaseReference myRefTidakHadir = database.getReference()
                .child("Kehadiran")
                .child("NPP")
                .child("1202170038")
                .child("AbsenDatang")
                .child(bln);
        Query queryTidakHadir = myRefTidakHadir.orderByChild("status").equalTo("Tidak Hadir");
        ValueEventListener eventListenerTidakHadir = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                countTidakHadir = dataSnapshot.getChildrenCount();
                Log.d("TAG", String.valueOf(countTidakHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        queryTidakHadir.addValueEventListener(eventListenerTidakHadir);


        final List<PieEntry> value = new ArrayList<>();
        DatabaseReference myRef = database.getReference()
                .child("Kehadiran")
                .child("NPP")
                .child("1202170038")
                .child("AbsenDatang")
                .child(bln);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                jmlhTerlambat = (float)countTerlambat;
                jmlhHadir = (float)countHadir;
                jmlhTidakHadir = (float)countTidakHadir;

                value.add(new PieEntry(0+jmlhTerlambat, "Terlambat"));
                value.add(new PieEntry(0+jmlhHadir, "Hadir"));
                value.add(new PieEntry(0+jmlhTidakHadir, "Tidak Hadir"));

                PieDataSet pieDataSet = new PieDataSet(value,"- Status Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200,1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(14f);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
