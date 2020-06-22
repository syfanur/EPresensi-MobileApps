package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;

public class Login extends AppCompatActivity {

    EditText npp, password;
    AwesomeText ImgShowHidePassword;

    boolean pwd_status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        npp = (EditText) findViewById(R.id.npp);
        password = (EditText) findViewById(R.id.password);

        ImgShowHidePassword = (AwesomeText)findViewById(R.id.ImgShowPassword);

        ImgShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    password.setSelection(password.length());
                } else {
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    password.setSelection(password.length());
                }
            }
        });


    }

    public void MoveToHomepage(View view) {
        Intent i = new Intent(Login.this,Homepage.class);
        startActivity(i);
    }
}
