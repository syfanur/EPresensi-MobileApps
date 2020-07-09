package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.O)
public class HalamanSelfie extends AppCompatActivity {
TextView rLokasiabsen;
String mLokasi="";
    Button btnDatang, btnPulang;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("Kehadiran");

    DateTimeFormatter formattertime = DateTimeFormatter.ofPattern("h:mm a");
    DateTimeFormatter formatterdate = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");

    //GetBulan
    LocalDate thismonth = LocalDate.now();
    Month currentMonth = thismonth.getMonth();
    String bln = String.valueOf(currentMonth);

    //GetTahun
    LocalDate thisyear = LocalDate.now();
    int currentYear = thisyear.getYear();
    String thn = String.valueOf(currentYear);

    boolean mDatang=false;
    boolean mPulang=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_selfie);
        rLokasiabsen=(TextView)findViewById(R.id.rLokasi);
        mLokasi = HalamanSelfie.this.getIntent().getStringExtra("lokasiAbsen");
        btnDatang = findViewById(R.id.btn_datang);
        btnPulang = findViewById(R.id.btn_pulang);

        //GetTanggal
        LocalDate today = LocalDate.now();
        String tgl = today.format(formatterdate);

        //GetJam
        LocalTime now = LocalTime.now();
       String jam = now.format(formattertime);


        myRef.child("1334").child(thn).child(bln).child(tgl)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("absenDatang").exists()) {
                            if(dataSnapshot.child("absenDatang").getValue(String.class).equals("Datang"))
                            mDatang=true;

                        }
                        if (dataSnapshot.child("absenPulang").exists()){
                            if(dataSnapshot.child("absenPulang").getValue(String.class).equals("Pulang"))
                                mPulang=true;
                        }
                                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                            btnPulang.setEnabled(false);
                            myRef.child("1334").child(thn).child(bln).child(tgl)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                btnPulang.setEnabled(true);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });

                            btnDatang.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (mDatang) {
                                        Toast.makeText(HalamanSelfie.this, "Anda Sudah Melakukan Absen Datang", Toast.LENGTH_SHORT).show();

                                    }else {
                                        Intent home = new Intent(HalamanSelfie.this, FaceDetectDatang.class);
                                        home.putExtra("lokasiAbsenDatang", mLokasi);
                                        startActivity(home);
                                    }
                                }
                            });


                            btnPulang.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (mPulang) {
                                        Toast.makeText(HalamanSelfie.this, "Anda Sudah Melakukan Absen Pulang", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent intent = new Intent(HalamanSelfie.this, FaceDetectPulang.class);
                                        intent.putExtra("lokasiAbsenPulang", mLokasi);
                                        startActivity(intent);
                                    }
                                }
                            });


                        }

//    public void MoveToFaceDetectDatang(View view) {
//        Intent home = new Intent(HalamanSelfie.this, FaceDetectDatang.class);
//        home.putExtra("lokasiAbsenDatang", mLokasi);
//        startActivity(home);
//
//    }
//
//
//    public void MoveToFaceDetectPulang(View view) {
//        Intent intent = new Intent(HalamanSelfie.this, FaceDetectPulang.class);
//       intent.putExtra("lokasiAbsenPulang", mLokasi);
//        startActivity(intent);
//    }
                    }


