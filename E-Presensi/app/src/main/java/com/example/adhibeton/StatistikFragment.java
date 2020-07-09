package com.example.adhibeton;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class StatistikFragment extends Fragment {
    private long countTerlambat, countTepatWaktu, countTidakHadir;
    private float jmlhTerlambat, jmlhTepatWaktu, jmlhTidakHadir;

    private Spinner spinnerBulan;
    private Spinner spinnerTahun;
    private PieChart pieChart;
    String bulan = "", tahun = "";

    //GetBulan
    LocalDate today = LocalDate.now();
    Month currentMonth = today.getMonth();
    String bln = String.valueOf(currentMonth);

    //GetTahun
    LocalDate thisyear = LocalDate.now();
    int currentYear = thisyear.getYear();
    String thn = String.valueOf(currentYear);

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final List<PieEntry> value = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_piechart_absen, viewGroup, false);

        pieChart = root.findViewById(R.id.pieChart);
        Button btn_filter = root.findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFilterWaktu();
            }
        });

        totalKeseluruhan();
        return root;
    }

    private void DialogFilterWaktu() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.filter_waktu, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_baseline_filter_list_24);
        dialog.setTitle("Filter by period");

        spinnerBulan = dialogView.findViewById(R.id.spBulan);
        spinnerTahun = dialogView.findViewById(R.id.spTahun);

        List<String> cBulan = new ArrayList<>();
        cBulan.add("ALL");cBulan.add("JANUARY");cBulan.add("FEBRUARY");cBulan.add("MARCH");cBulan.add("APRIL");
        cBulan.add("MAY");cBulan.add("JUNE");cBulan.add("JULY");cBulan.add("AUGUST");cBulan.add("SEPTEMBER");
        cBulan.add("OCTOBER");cBulan.add("NOVEMBER");cBulan.add("DECEMBER");

        List<String> cTahun = new ArrayList<>();
        cTahun.add("ALL");cTahun.add("2020");cTahun.add("2019");cTahun.add("2018");cTahun.add("2017");cTahun.add("2016");
        cTahun.add("2015");cTahun.add("2014");cTahun.add("2013");cTahun.add("2012");cTahun.add("2011");
        cTahun.add("2010");cTahun.add("2009");

        ArrayAdapter<String> wAdapter;
        wAdapter= new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cBulan);
        wAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBulan.setAdapter(wAdapter);

        ArrayAdapter<String> wAdapterTahun;
        wAdapterTahun= new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, cTahun);
        wAdapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTahun.setAdapter(wAdapterTahun);

        dialog.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bulan = spinnerBulan.getSelectedItem().toString();
                tahun = spinnerTahun.getSelectedItem().toString();

                countTepatWaktu = 0; countTerlambat = 0; countTidakHadir = 0;
                value.clear();

                if (bulan.equals("ALL")) {
                    thn = tahun;
                    totalSetahun();
                } else if (bln.equals("ALL") && thn.equals("ALL")) {
                    restartActivity();
                } else {
                    thn = tahun;
                    bln = bulan;
                    totalSebulan();
                }
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void restartActivity(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(this).attach(this).commit();
    }

    private void totalSebulan() {
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(480, 500);
        desc.setTextSize(14f);

        pieChart.setDescription(desc);

        DatabaseReference myRefTotalSebulan = database.getReference()
                .child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp())
                .child(thn)
                .child(bln);
        myRefTotalSebulan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()) {
                    switch (uniqueKey1.child("statusDatang").getValue(String.class)) {
                        case "Terlambat":
                            ++countTerlambat;
                            break;
                        case "Tepat Waktu":
                            ++countTepatWaktu;
                            break;
                        case "Tidak Hadir":
                            ++countTidakHadir;
                            break;
                    }
                }
                jmlhTerlambat = (float) countTerlambat;
                jmlhTepatWaktu = (float) countTepatWaktu;
                jmlhTidakHadir = (float) countTidakHadir;

                if (jmlhTerlambat != 0) {
                    value.add(new PieEntry(0 + jmlhTerlambat, "Terlambat"));
                }

                if (jmlhTepatWaktu != 0) {
                    value.add(new PieEntry(0 + jmlhTepatWaktu, "Tepat Waktu"));
                }

                if (jmlhTidakHadir != 0) {
                    value.add(new PieEntry(0 + jmlhTidakHadir, "Tidak Hadir"));
                }

                PieDataSet pieDataSet = new PieDataSet(value, "- Status Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200, 1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(14f);

                Log.d("Terlambat", String.valueOf(countTerlambat));
                Log.d("Tepat Waktu", String.valueOf(countTepatWaktu));
                Log.d("Tidak Hadir", String.valueOf(countTidakHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void totalSetahun() {
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(480, 500);
        desc.setTextSize(14f);

        pieChart.setDescription(desc);

        DatabaseReference myRefTotalSetahun = database.getReference()
                .child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp())
                .child(thn);
        myRefTotalSetahun.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot uniqueKey2 : uniqueKey1.getChildren()) {
                        switch (uniqueKey2.child("statusDatang").getValue(String.class)) {
                            case "Terlambat":
                                ++countTerlambat;
                                break;
                            case "Tepat Waktu":
                                ++countTepatWaktu;
                                break;
                            case "Tidak Hadir":
                                ++countTidakHadir;
                                break;
                        }
                    }
                }
                jmlhTerlambat = (float) countTerlambat;
                jmlhTepatWaktu = (float) countTepatWaktu;
                jmlhTidakHadir = (float) countTidakHadir;

                if (jmlhTerlambat != 0) {
                    value.add(new PieEntry(0 + jmlhTerlambat, "Terlambat"));
                }

                if (jmlhTepatWaktu != 0) {
                    value.add(new PieEntry(0 + jmlhTepatWaktu, "Tepat Waktu"));
                }

                if (jmlhTidakHadir != 0) {
                    value.add(new PieEntry(0 + jmlhTidakHadir, "Tidak Hadir"));
                }

                PieDataSet pieDataSet = new PieDataSet(value, "- Status Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200, 1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(14f);

                Log.d("Terlambat", String.valueOf(countTerlambat));
                Log.d("Tepat Waktu", String.valueOf(countTepatWaktu));
                Log.d("Tidak Hadir", String.valueOf(countTidakHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void totalKeseluruhan() {
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(14f);

        Description desc = new Description();
        desc.setText("Persentase Kehadiran");
        desc.setPosition(480, 500);
        desc.setTextSize(14f);

        pieChart.setDescription(desc);

        DatabaseReference myRefTotalKeseluruhan = database.getReference()
                .child("Kehadiran")
                .child(Prevalent.currentOnlineUser.getNpp());
        myRefTotalKeseluruhan.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot uniqueKey2 : uniqueKey1.getChildren()) {
                        for (DataSnapshot uniqueKey3 : uniqueKey2.getChildren()) {
                            switch (uniqueKey3.child("statusDatang").getValue(String.class)) {
                                case "Terlambat":
                                    ++countTerlambat;
                                    break;
                                case "Tepat Waktu":
                                    ++countTepatWaktu;
                                    break;
                                case "Tidak Hadir":
                                    ++countTidakHadir;
                                    break;
                            }
                        }
                    }
                }
                jmlhTerlambat = (float) countTerlambat;
                jmlhTepatWaktu = (float) countTepatWaktu;
                jmlhTidakHadir = (float) countTidakHadir;

                if (jmlhTerlambat != 0) {
                    value.add(new PieEntry(0 + jmlhTerlambat, "Terlambat"));
                }

                if (jmlhTepatWaktu != 0) {
                    value.add(new PieEntry(0 + jmlhTepatWaktu, "Tepat Waktu"));
                }

                if (jmlhTidakHadir != 0) {
                    value.add(new PieEntry(0 + jmlhTidakHadir, "Tidak Hadir"));
                }

                PieDataSet pieDataSet = new PieDataSet(value, "- Status Kehadiran");
                PieData pieData = new PieData(pieDataSet);

                pieChart.setData(pieData);
                pieChart.animateXY(1200, 1200);

                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                pieDataSet.setValueTextSize(14f);

                Log.d("Terlambat", String.valueOf(countTerlambat));
                Log.d("Tepat Waktu", String.valueOf(countTepatWaktu));
                Log.d("Tidak Hadir", String.valueOf(countTidakHadir));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
