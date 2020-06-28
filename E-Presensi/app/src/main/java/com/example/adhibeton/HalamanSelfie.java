package com.example.adhibeton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HalamanSelfie extends AppCompatActivity {
//    AlertDialog.Builder dialog;
//    LayoutInflater inflater;
//    View dialogView;
//    TextView mStatus, mTanggal, mJam;
//
//    @SuppressLint("SimpleDateFormat")
//    DateFormat jf = new SimpleDateFormat("h:mm a");
//    @SuppressLint("SimpleDateFormat")
//    DateFormat tf = new SimpleDateFormat("EEE, d MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_selfie);
    }

    public void MoveToFaceDetect(View view) {
        Intent home = new Intent(HalamanSelfie.this, FaceDetect.class);
        startActivity(home);
//        dialog = new AlertDialog.Builder(HalamanSelfie.this);
//        inflater = getLayoutInflater();
//        dialogView = inflater.inflate(R.layout.absen_datang, null);
//        dialog.setView(dialogView);
//        dialog.setCancelable(false);
//        dialog.setTitle("Detail Absen");
//        mStatus=(TextView)dialogView.findViewById(R.id.Status);
//        mJam=(TextView)dialogView.findViewById(R.id.jam);
//        mTanggal=(TextView)dialogView.findViewById(R.id.tanggal);
//       final String jam = jf.format(Calendar.getInstance().getTime());
//       final String tgl = tf.format(Calendar.getInstance().getTime());
//       final String status="Pulang";
//
//        mStatus.setText(status);
//        mJam.setText(jam);
//        mTanggal.setText(tgl);
//
//        dialog.setPositiveButton("OKE", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                Intent intent = new Intent(HalamanSelfie.this, HomeScreen.class);
//                intent.putExtra("status", status);
//                intent.putExtra("jam",jam );
//                intent.putExtra("tanggal", tgl);
//        startActivity(intent);
//
//            }
//        });
//        dialog.show();
    }
}
