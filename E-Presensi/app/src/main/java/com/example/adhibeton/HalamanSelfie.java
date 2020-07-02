package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HalamanSelfie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_selfie);
    }

    public void MoveToFaceDetectDatang(View view) {
        Intent home = new Intent(HalamanSelfie.this, FaceDetectDatang.class);
        startActivity(home);
    }

    public void MoveToFaceDetectPulang(View view) {
        Intent home = new Intent(HalamanSelfie.this, FaceDetectPulang.class);
        startActivity(home);
    }
}
