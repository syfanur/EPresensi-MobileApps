package com.example.e_presensiadhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class PiechartAbsen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart_absen);

        PieChart pieChart = findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(780,780);
        desc.setTextSize(18f);

        pieChart.setDescription(desc);

        List<PieEntry> value = new ArrayList<>();
        value.add(new PieEntry(20f, "Tidak Hadir"));
        value.add(new PieEntry(30f, "Terlambat"));
        value.add(new PieEntry(50f, "Tepat Waktu"));

        PieDataSet pieDataSet = new PieDataSet(value,"Kehadiran");
        PieData pieData = new PieData(pieDataSet);

        pieChart.setData(pieData);

        pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        pieChart.animateXY(1200,1200);
    }
}
