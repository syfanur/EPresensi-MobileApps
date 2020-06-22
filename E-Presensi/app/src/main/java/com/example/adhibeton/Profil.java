package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Profil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
    }

    public void editpass(View view) {
        Intent a = new Intent(Profil.this, EditPassword.class);
        startActivity(a);
    }

    public void editdata(View view) {
        Intent b = new Intent(Profil.this, EditData.class);
        startActivity(b);
    }

    public void logout(View view) {
        Intent z = new Intent(Profil.this, Login.class);
        startActivity(z);
    }
}
