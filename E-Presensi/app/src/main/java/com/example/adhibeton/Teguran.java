package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Teguran extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teguran);
    }

    public void back(View view) {
        Intent i = new Intent(Teguran.this,HomeScreen.class);
        startActivity(i);
    }
}
