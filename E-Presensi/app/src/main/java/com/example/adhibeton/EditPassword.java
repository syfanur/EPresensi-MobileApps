package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class EditPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        CircularImageView profil = findViewById(R.id.profil);
        Picasso.get().load(Prevalent.currentOnlineUser.getProfil()).into(profil);
    }

    public void back(View view) {
        Intent i = new Intent(EditPassword.this,HomeScreen.class);
        startActivity(i);
    }
}
