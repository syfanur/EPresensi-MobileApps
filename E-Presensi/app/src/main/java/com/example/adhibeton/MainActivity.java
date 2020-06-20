package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        Intent a = new Intent(MainActivity.this, Login.class);
        startActivity(a);
    }

    public void nomeet(View view) {
        Intent b = new Intent(MainActivity.this, no_meeting.class);
        startActivity(b);
    }

    public void profile(View view) {
        Intent c = new Intent(MainActivity.this, Profil.class);
        startActivity(c);
    }

    public void buatizin(View view) {
        Intent d = new Intent(MainActivity.this, buatizin.class);
        startActivity(d);
    }

    public void detailizin(View view) {
        Intent e = new Intent(MainActivity.this, detailizin.class);
        startActivity(e);
    }



    public void detailabsen(View view) {
        Intent g = new Intent(MainActivity.this, DetailKehadiran.class);
        startActivity(g);
    }

    public void perizinan(View view) {
        Intent h = new Intent(MainActivity.this, Perizinan.class);
        startActivity(h);
    }
}
