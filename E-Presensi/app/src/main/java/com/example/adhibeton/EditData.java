package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD
=======
import android.content.Intent;
>>>>>>> syfa
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class EditData extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);


    }

<<<<<<< HEAD
=======
    public void back(View view) {
        Intent i = new Intent(EditData.this,Profil.class);
        startActivity(i);
    }

>>>>>>> syfa

}