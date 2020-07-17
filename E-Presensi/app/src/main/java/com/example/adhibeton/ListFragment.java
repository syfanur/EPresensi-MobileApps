package com.example.adhibeton;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ListFragment extends Fragment {
    RecyclerView mRecyclerView;
    FirebaseDatabase mfirebaseDatabase;
    private SimpleDateFormat dateFormatter;
    DatabaseReference mRef;
    LinearLayoutManager mLayoutManager;
    LayoutInflater inflater;
    AlertDialog.Builder dialog;
    View dialogView;
    FirebaseRecyclerAdapter<ModelAbsenKehadiran, AdapterKehadiran.MyViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<ModelAbsenKehadiran> options;
    Button btn_filter;
    SharedPreferences mSharePreferences;
    private DatePickerDialog datePickerDialog;
    private TextView tvDateResult;
    private Spinner spinnerBulan;
    private Spinner spinnerTahun;
    private List<ModelAbsenKehadiran> listData;
    private AdapterKehadiran adapter;
    String bulan="";
    String tahun="";

    DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("h:mm a");
    DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    //GetBulan
    LocalDate today = LocalDate.now();
    Month currentMonth = today.getMonth();
    String bln = String.valueOf(currentMonth);

    //GetTahun
    LocalDate thisyear = LocalDate.now();
    int currentYear = thisyear.getYear();
    String thn = String.valueOf(currentYear);

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.list_kehadiran, viewGroup, false);

       //INISIALISAI
        mRecyclerView= v.findViewById(R.id.recyclerView);
        tvDateResult = (TextView)v.findViewById(R.id.tv_dateresult);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listData= new ArrayList<>();

        //SORT DATA
        mSharePreferences=this.getActivity().getSharedPreferences("SortSetting", MODE_PRIVATE);
        String mSorting =mSharePreferences.getString("Sort", "newest");

        if (mSorting.equals("newest")){
            mLayoutManager= new LinearLayoutManager(getContext());
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        }
        else if(mSorting.equals("oldest")){
            mLayoutManager= new LinearLayoutManager(getContext());
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }

        //GET DATA FROM FIREBASE
        mfirebaseDatabase = FirebaseDatabase.getInstance();
        mRef = mfirebaseDatabase.getReference("Kehadiran");

        showListData();
//        FilterPeriodKehadiranSemuaBulan();

        //INTENT TO DIALOG FILTER
        btn_filter=(Button)v.findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterDialog();
            }
        });

        showListData();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void showListData(){
        listData.clear();
        mRef.child(Prevalent.currentOnlineUser.getNpp()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()){
                    for (DataSnapshot uniqueKey2 : uniqueKey1.getChildren()){
                        for (DataSnapshot uniqueKey3 : uniqueKey2.getChildren()){
                            ModelAbsenKehadiran ma = uniqueKey3.getValue(ModelAbsenKehadiran.class);
                            listData.add(ma);
                        }
                        adapter=new AdapterKehadiran(getContext(),listData);
                        mRecyclerView.setAdapter(adapter);
                    }
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    private void FirebaseFilterWaktu(final String bulan, final String tahun) {
//
//      mRef.child("1334").child("2020").child("JULY").addChildEventListener(new ChildEventListener() {
//          @Override
//          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//              String id = dataSnapshot.getKey();
//              if (dataSnapshot.child("bulan").exists() && dataSnapshot.child("tahun").exists()) {
//                  if (dataSnapshot.child("bulan").getValue(String.class).equals(bulan) && dataSnapshot.child("tahun").getValue(String.class).equals(tahun)) {
//                      ModelAbsenKehadiran ma = dataSnapshot.getValue(ModelAbsenKehadiran.class);
//                      listData.add(ma);
//                  }
//
//              }else {
//                  showListData();
//              }
//
//              adapter=new AdapterKehadiran(getContext(),listData);
//              mRecyclerView.setAdapter(adapter);
//          }
//
//          @Override
//          public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//          }
//
//          @Override
//          public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//          }
//
//          @Override
//          public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//          }
//
//          @Override
//          public void onCancelled(@NonNull DatabaseError databaseError) {
//
//          }
//      });
//        }
    private void FilterPeriodKehadiranBulan(){
        listData.clear();
        mRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).child(bln).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()){
                    ModelAbsenKehadiran ma = uniqueKey1.getValue(ModelAbsenKehadiran.class);
                    listData.add(ma);
                }
                adapter=new AdapterKehadiran(getContext(),listData);
                mRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void FilterPeriodKehadiranTahun() {
        mRef.child(Prevalent.currentOnlineUser.getNpp()).child(thn).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot uniqueKey2 : uniqueKey1.getChildren()) {
                        ModelAbsenKehadiran ma = uniqueKey2.getValue(ModelAbsenKehadiran.class);
                        listData.add(ma);
                    }
                    adapter = new AdapterKehadiran(getContext(), listData);
                    mRecyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//    private void FilterPeriodKehadiranSemuaBulan() {
//        mRef.child("1334").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot uniqueKey1 : dataSnapshot.getChildren()) {
//                    for (DataSnapshot uniqueKey2 : uniqueKey1.getChildren()) {
//                            for (DataSnapshot uniqueKey3 : uniqueKey2.getChildren()) {
//                                if(uniqueKey3.child("bulan").getValue(String.class).equals(bln)){
//                                    ModelAbsenKehadiran ma = uniqueKey3.getValue(ModelAbsenKehadiran.class);
//                                    listData.add(ma);
//                                }
//                                adapter = new AdapterKehadiran(getContext(), listData);
//                                mRecyclerView.setAdapter(adapter);
//                            }
//
//                        }
//
//
//                    }

//                }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
    private void showFilterDialog() {
        String[] sortOptions = {"Waktu", "Sort by"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Filter Category")
                .setIcon(R.drawable.ic_baseline_filter_list_24)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {

                            DialogFilterWaktu();
                        } else if (which == 1) {
                            showSortDialog();
                        }
                    }
                });
        builder.show();
    }

    private void DialogFilterWaktu() {
        dialog = new AlertDialog.Builder(getContext());
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.filter_waktu, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.ic_baseline_filter_list_24);
        dialog.setTitle("Filter by period");
        spinnerBulan= (Spinner)dialogView.findViewById(R.id.spBulan);
        spinnerTahun= (Spinner)dialogView.findViewById(R.id.spTahun);
        List<String> cBulan = new ArrayList<>();
        cBulan.add("ALL");
        cBulan.add("JANUARY");
        cBulan.add("FEBRUARY");
        cBulan.add("MARCH");
        cBulan.add("APRIL");
        cBulan.add("MAY");
        cBulan.add("JUNE");
        cBulan.add("JULY");
        cBulan.add("AUGUST");
        cBulan.add("SEPTEMBER");
        cBulan.add("OCTOBER");
        cBulan.add("NOVEMBER");
        cBulan.add("DECEMBER");

        List<String> cTahun = new ArrayList<>();
        cTahun.add("ALL");
        cTahun.add("2020");
        cTahun.add("2019");
        cTahun.add("2018");
        cTahun.add("2017");
        cTahun.add("2016");
        cTahun.add("2015");
        cTahun.add("2014");
        cTahun.add("2013");
        cTahun.add("2012");
        cTahun.add("2011");
        cTahun.add("2010");
        cTahun.add("2009");

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
                listData.clear();
                bulan = spinnerBulan.getSelectedItem().toString();
                tahun = spinnerTahun.getSelectedItem().toString();
                listData.clear();

                if (bulan.equals("ALL"))
                    thn = tahun;
                FilterPeriodKehadiranTahun();
                if (bulan.equals("ALL") && tahun.equals("ALL")) {
                    showListData();
                    if (thn == tahun && bln == bulan) {

                        thn = tahun;
                        bln = bulan;
                        FilterPeriodKehadiranBulan();
                    }else {

                    }
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

    private void showSortDialog() {
        String [] sortOptions = {"Newest", "Oldest"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Sort by")
                .setIcon(R.drawable.ic_baseline_sort_24)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){

                            SharedPreferences.Editor editor = mSharePreferences.edit();
                            editor.putString("Sort", "newest");
                            editor.apply();
                            getActivity().recreate();


                        }
                        else if (which==1){{
                            SharedPreferences.Editor editor = mSharePreferences.edit();
                            editor.putString("Sort", "oldest");
                            editor.apply();
                            getActivity().recreate();


                        }}


                    }
                });
        builder.show();
    }

//    private void showdata(){
//        options= new FirebaseRecyclerOptions.Builder<ModelKehadiran>().setQuery( mRef.child("1234").child("AbsenDatang").child("2020").child("JULY"), ModelKehadiran.class).build();
//        firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<ModelKehadiran, HolderView>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull final HolderView holder, int i, @NonNull ModelKehadiran model) {
//                holder.setDetails(getContext(),model.getAbsen(), model.getKeterangan(),model.getLokasi(),model.getStatus(),model.getTanggal(),model.getWaktu(),model.getBulan(),model.getTahun());
//            mRef=FirebaseDatabase.getInstance().getReference().child("Kehadiran").child("1234").child("AbsenPulang").child("2020").child("JULY");
//            mRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    String waktu= dataSnapshot.child("waktu").getValue().toString();
//                    holder.mAbsen_pulang.setText(waktu);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

//
//            @NonNull
//            @Override
//            public HolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_kehadiran,parent,false);
//                HolderView holderView= new HolderView(itemView);
//                return holderView;
//            }
//        };
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        firebaseRecyclerAdapter.startListening();
//        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
//    }


//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if(firebaseRecyclerAdapter!=null){
//            firebaseRecyclerAdapter.startListening();
//        }
//    }
}
