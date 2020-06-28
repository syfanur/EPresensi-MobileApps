package com.example.e_presensiadhibeton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.e_presensiadhibeton.model.ModelAbsen;
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

import java.util.ArrayList;
import java.util.List;

public class PiechartAbsen extends AppCompatActivity {
    int count;
    String stat;
    float jmlh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart_absen);

        final PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(18f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(830,800);
        desc.setTextSize(24f);

        pieChart.setDescription(desc);

        final List<PieEntry> value = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Kehadiran");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                value.clear();
                count = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ModelAbsen km = ds.getValue(ModelAbsen.class);
                    jmlh = dataSnapshot.getChildrenCount();
                    stat = km.getStatus();

                    value.add(new PieEntry(0+jmlh, ""+stat));
                    count++;
                }
                PieDataSet pieDataSet = new PieDataSet(value,"- Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200,1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(24f);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
