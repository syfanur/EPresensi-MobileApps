package com.example.e_presensiadhibeton;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.e_presensiadhibeton.model.ModelAbsen;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.L;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import dmax.dialog.SpotsDialog;

import java.io.IOException;
import java.util.Locale;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ScannedBarcodeActivity extends AppCompatActivity {

    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;
    SurfaceView surfaceView;
    Button btnDatang, btnPulang;
    String intentData = "", jam = "", tgl = "", status = "";
    String lokasi = "Jl. Ciparay No 20B Kujangsari, Bandung Kidul, Kota Bandung";

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

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Kehadiran");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanned_barcode);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        surfaceView = findViewById(R.id.surfaceView);
        btnDatang = findViewById(R.id.btnDatang);
        btnPulang = findViewById(R.id.btnPulang);
        btnPulang.setEnabled(false);
        myRef.child("1202170038").child("AbsenDatang").child(thn).child(bln)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(tgl).exists()){
                            btnPulang.setEnabled(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        btnDatang.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (intentData.length() > 0) {
                    if (intentData.equals("test@123absenkehadiranpegawai")){

                        checkDatang(jam);
                        checkWeekDay();

                        if (status.equals("Belum Hadir")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!isFinishing()){
                                        new AlertDialog.Builder(ScannedBarcodeActivity.this)
                                                .setTitle("INFO")
                                                .setMessage("Anda absen diluar jam kerja")
                                                .setCancelable(false)
                                                .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        onPause();
                                                        dialog.dismiss();
                                                        onResume();
                                                    }
                                                }).show();
                                    }
                                }
                            });
                        } else {
                            myRef.child("1202170038").child("AbsenDatang").child(thn).child(bln).orderByChild("tanggal").equalTo(tgl)
                                    .addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (!isFinishing()){
                                                            new AlertDialog.Builder(ScannedBarcodeActivity.this)
                                                                    .setTitle("INFO")
                                                                    .setMessage("Anda sudah melakukan absen datang")
                                                                    .setCancelable(false)
                                                                    .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(DialogInterface dialog, int which) {
                                                                            onPause();
                                                                            dialog.dismiss();
                                                                            onResume();
                                                                        }
                                                                    }).show();
                                                        }
                                                    }
                                                });
                                            } else {
                                                //Input ke database
                                                ModelAbsen absen = new ModelAbsen(tgl, jam, "Datang", status, intentData, lokasi);
                                                myRef.child("1202170038").child("AbsenDatang").child(thn).child(bln).child(tgl).setValue(absen);
                                                Toast.makeText(ScannedBarcodeActivity.this, "Absen Datang Berhasil", Toast.LENGTH_SHORT).show();

//                                                startActivity(new Intent(ScannedBarcodeActivity.this, HasilTestScan.class));
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                            Toast.makeText(ScannedBarcodeActivity.this, "Gagal terkoneksi ke database", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        //Validasi QR Code
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isFinishing()){
                                    new AlertDialog.Builder(ScannedBarcodeActivity.this)
                                            .setTitle("INFO")
                                            .setMessage("Barcode yang anda scan tidak valid")
                                            .setCancelable(false)
                                            .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    onPause();
                                                    dialog.dismiss();
                                                    onResume();
                                                }
                                            }).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(ScannedBarcodeActivity.this, "Barcode tidak terdeteksi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnPulang.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (intentData.length() > 0) {
                    if (intentData.equals("test@123absenkehadiranpegawai")){

                        checkPulang(jam);
                        checkWeekDay();

                        myRef.child("1202170038").child("AbsenPulang").child(thn).child(bln).orderByChild("tanggal").equalTo(tgl).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (!isFinishing()){
                                                new AlertDialog.Builder(ScannedBarcodeActivity.this)
                                                        .setTitle("INFO")
                                                        .setMessage("Anda sudah melakukan absen pulang")
                                                        .setCancelable(false)
                                                        .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                onPause();
                                                                dialog.dismiss();
                                                                onResume();
                                                            }
                                                        }).show();
                                            }
                                        }
                                    });
                                } else {
                                    //Input ke database
                                    ModelAbsen absen = new ModelAbsen(tgl, jam, "Pulang", status, intentData, lokasi);
                                    myRef.child("1202170038").child("AbsenPulang").child(thn).child(bln).child(tgl).setValue(absen);
                                    Toast.makeText(ScannedBarcodeActivity.this, "Absen Pulang Berhasil", Toast.LENGTH_SHORT).show();

//                                    startActivity(new Intent(ScannedBarcodeActivity.this, HasilTestScan.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(ScannedBarcodeActivity.this, "Gagal terkoneksi ke database", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        //Validasi QR Code
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!isFinishing()){
                                    new AlertDialog.Builder(ScannedBarcodeActivity.this)
                                            .setTitle("INFO")
                                            .setMessage("Barcode yang anda scan tidak valid")
                                            .setCancelable(false)
                                            .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    onPause();
                                                    dialog.dismiss();
                                                    onResume();
                                                }
                                            }).show();
                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(ScannedBarcodeActivity.this, "Barcode tidak terdeteksi", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initialiseDetectorsAndSources() {
        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
                Toast.makeText(ScannedBarcodeActivity.this, "Barcode scanner stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            intentData = barcodes.valueAt(0).displayValue;
                            //GetTanggal
                            LocalDate today = LocalDate.now();
                            tgl = today.format(formatterdate);

                            //GetJam
                            LocalTime now = LocalTime.now();
                            jam = now.format(formattertime);

                            Toast.makeText(ScannedBarcodeActivity.this, "Barcode scanned!", Toast.LENGTH_SHORT).show();
                            cameraSource.stop();
                        }
                    });
                }
            }
        });
    }

    //Cek Status Kehadiran Datang
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkDatang(String checkTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
        LocalTime startLocalTime = LocalTime.parse("9:00 AM", formatter);
        LocalTime lateLocalTime = LocalTime.parse("9:15 AM", formatter);
        LocalTime endLocalTime = LocalTime.parse("5:00 PM", formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

        boolean isHadir = false;
        boolean isTelat = false;
        if (endLocalTime.isAfter(startLocalTime) || lateLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && lateLocalTime.isAfter(checkLocalTime)) {
                isHadir = true;
            } else if (lateLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)){
                isTelat = true;
            }
        }

        if (isHadir) {
            status = "Tepat Waktu";
        } else if (isTelat){
            status = "Terlambat";
        } else {
            status = "Belum Hadir";
        }
    }

    //Cek Status Kehadiran Pulang
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void checkPulang(String checkTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
        LocalTime startLocalTime = LocalTime.parse("9:00 AM", formatter);
        LocalTime endLocalTime = LocalTime.parse("5:00 PM", formatter);
        LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

        boolean isInBetween = false;
        if (endLocalTime.isAfter(startLocalTime)) {
            if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
                isInBetween = true;
            }
        } else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
            isInBetween = true;
        }

        if (isInBetween) {
            status = "Didalam Jam";
        } else {
            status = "Diluar Jam";
        }
    }

    //Cek Status Kehadiran Jam Libur
    protected void checkWeekDay(){
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.SATURDAY == dayOfWeek || Calendar.SUNDAY == dayOfWeek) {
            status = "Libur Kerja";
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        surfaceView.setVisibility(View.GONE);
        intentData = "";
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
        surfaceView.setVisibility(View.VISIBLE);
    }
}
