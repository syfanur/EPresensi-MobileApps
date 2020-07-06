package com.example.adhibeton;

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
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PiechartAbsen extends AppCompatActivity {
    long countTerlambat, countTepatWaktu, countTidakHadir;
    float jmlhTerlambat, jmlhHadir, jmlhTidakHadir;
    PieChart pieChart;

    //GetBulan
    LocalDate today = LocalDate.now();
    Month currentMonth = today.getMonth();
    String bln = String.valueOf(currentMonth);

    //GetTahun
    LocalDate thisyear = LocalDate.now();
    int currentYear = thisyear.getYear();
    String thn = String.valueOf(currentYear);

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final List<PieEntry> value = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pieChart = findViewById(R.id.pieChart);
//        totalKeseluruhan();
//        totalSetahun();
        totalSebulan();
    }

    protected void totalSebulan(){
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(480,500);
        desc.setTextSize(14f);

        pieChart.setDescription(desc);

        DatabaseReference myRefTotalSetahun = database.getReference()
                .child("Kehadiran")
                .child("1202170038")
                .child("AbsenDatang")
                .child(thn)
                .child(bln);
        myRefTotalSetahun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()){
                    switch (uniqueKey1.child("status").getValue(String.class)){
                        case "Terlambat":
                            ++countTerlambat;
                            break;
                        case "Hadir":
                            ++countTepatWaktu;
                            break;
                        case "Tidak Hadir":
                            ++countTidakHadir;
                            break;
                    }
                }
                jmlhTerlambat = (float)countTerlambat;
                jmlhHadir = (float)countTepatWaktu;
                jmlhTidakHadir = (float)countTidakHadir;

                if (jmlhTerlambat != 0){
                    value.add(new PieEntry(0+jmlhTerlambat, "Terlambat"));
                }

                if (jmlhHadir != 0){
                    value.add(new PieEntry(0+jmlhHadir, "Hadir"));
                }

                if (jmlhTidakHadir != 0){
                    value.add(new PieEntry(0+jmlhTidakHadir, "Tidak Hadir"));
                }

                PieDataSet pieDataSet = new PieDataSet(value,"- Status Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200,1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(14f);

                Log.d("Terlambat", String.valueOf(countTerlambat));
                Log.d("Hadir", String.valueOf(countTepatWaktu));
                Log.d("Tidak Hadir", String.valueOf(countTidakHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void totalSetahun(){
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(480,500);
        desc.setTextSize(14f);

        pieChart.setDescription(desc);

        DatabaseReference myRefTotalSetahun = database.getReference()
                .child("Kehadiran")
                .child("1202170038")
                .child("AbsenDatang")
                .child(thn);
        myRefTotalSetahun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()){
                    for (DataSnapshot uniqueKey2 : uniqueKey1.getChildren()){
                        switch (uniqueKey2.child("status").getValue(String.class)){
                            case "Terlambat":
                                ++countTerlambat;
                                break;
                            case "Hadir":
                                ++countTepatWaktu;
                                break;
                            case "Tidak Hadir":
                                ++countTidakHadir;
                                break;
                        }
                    }
                }
                jmlhTerlambat = (float)countTerlambat;
                jmlhHadir = (float)countTepatWaktu;
                jmlhTidakHadir = (float)countTidakHadir;

                if (jmlhTerlambat != 0){
                    value.add(new PieEntry(0+jmlhTerlambat, "Terlambat"));
                }

                if (jmlhHadir != 0){
                    value.add(new PieEntry(0+jmlhHadir, "Hadir"));
                }

                if (jmlhTidakHadir != 0){
                    value.add(new PieEntry(0+jmlhTidakHadir, "Tidak Hadir"));
                }

                PieDataSet pieDataSet = new PieDataSet(value,"- Status Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200,1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(14f);

                Log.d("Terlambat", String.valueOf(countTerlambat));
                Log.d("Hadir", String.valueOf(countTepatWaktu));
                Log.d("Tidak Hadir", String.valueOf(countTidakHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    protected void totalKeseluruhan(){
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(480,500);
        desc.setTextSize(14f);

        pieChart.setDescription(desc);

        DatabaseReference myRefTotalKeseluruhan = database.getReference()
                .child("Kehadiran")
                .child("1202170038")
                .child("AbsenDatang");
        myRefTotalKeseluruhan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()){
                    for (DataSnapshot uniqueKey2 : uniqueKey1.getChildren()){
                        for (DataSnapshot uniqueKey3 : uniqueKey2.getChildren()){
                            switch (uniqueKey3.child("status").getValue(String.class)){
                                case "Terlambat":
                                    ++countTerlambat;
                                    break;
                                case "Hadir":
                                    ++countTepatWaktu;
                                    break;
                                case "Tidak Hadir":
                                    ++countTidakHadir;
                                    break;
                            }
                        }
                    }
                }
                jmlhTerlambat = (float)countTerlambat;
                jmlhHadir = (float)countTepatWaktu;
                jmlhTidakHadir = (float)countTidakHadir;

                if (jmlhTerlambat != 0){
                    value.add(new PieEntry(0+jmlhTerlambat, "Terlambat"));
                }

                if (jmlhHadir != 0){
                    value.add(new PieEntry(0+jmlhHadir, "Hadir"));
                }

                if (jmlhTidakHadir != 0){
                    value.add(new PieEntry(0+jmlhTidakHadir, "Tidak Hadir"));
                }

                PieDataSet pieDataSet = new PieDataSet(value,"- Status Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200,1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(14f);

                Log.d("Terlambat", String.valueOf(countTerlambat));
                Log.d("Hadir", String.valueOf(countTepatWaktu));
                Log.d("Tidak Hadir", String.valueOf(countTidakHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
