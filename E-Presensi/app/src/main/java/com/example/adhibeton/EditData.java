package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditData extends AppCompatActivity {


    private EditText Pendidikan, Alamat, Goldarah, Agama, Nonpwp, Baju, Celana,
    Sepatu, Hp, HpKeluarga;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        CircularImageView profil = findViewById(R.id.profil);
        Picasso.get().load(Prevalent.currentOnlineUser.getProfil()).into(profil);

        Pendidikan = (EditText) findViewById(R.id.pendidikan);
        Alamat = (EditText) findViewById(R.id.alamat);
        Goldarah = (EditText) findViewById(R.id.goldarah);
        Agama = (EditText) findViewById(R.id.agama);
        Nonpwp = (EditText) findViewById(R.id.nonpwp);
        Baju = (EditText) findViewById(R.id.baju);
        Celana = (EditText) findViewById(R.id.celana);
        Sepatu = (EditText) findViewById(R.id.sepatu);
        Hp = (EditText) findViewById(R.id.hp);
        HpKeluarga = (EditText) findViewById(R.id.hpkeluarga);
        Button Submit = (Button) findViewById(R.id.submit);

        userInfoDisplay(Pendidikan, Alamat, Goldarah, Agama, Nonpwp, Baju, Celana, Sepatu, Hp, HpKeluarga);


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
                    userInfoSaved();
                }
                else
                {
                    updateOnlyUserInfo();
                }
            }
        });

    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Karyawan");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("pendidikan", Pendidikan.getText().toString());
        userMap. put("alamat", Alamat.getText().toString());
        userMap. put("goldar", Goldarah.getText().toString());
        userMap. put("agama", Agama.getText().toString());
        userMap. put("npwp", Nonpwp.getText().toString());
        userMap. put("ukuran_baju", Baju.getText().toString());
        userMap. put("ukuran_celana", Celana.getText().toString());
        userMap. put("ukuran_sepatu", Sepatu.getText().toString());
        userMap. put("no_hp", Hp.getText().toString());
        userMap. put("no_hpkeluarga", HpKeluarga.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getNpp()).updateChildren(userMap);


        Toast.makeText(EditData.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditData.this, HomeScreen.class));
        finish();
    }


    private void userInfoSaved() {
        if (TextUtils.isEmpty(Pendidikan.getText().toString()))
        {
            Toast.makeText(this, "Isi Pendidikan.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Alamat.getText().toString()))
        {
            Toast.makeText(this, "Isi Alamat.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Goldarah.getText().toString()))
        {
            Toast.makeText(this, "Isi Golongan Darah.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Agama.getText().toString()))
        {
            Toast.makeText(this, "Agama.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Nonpwp.getText().toString()))
        {
            Toast.makeText(this, "Isi No NPWP.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Baju.getText().toString()))
        {
            Toast.makeText(this, "Isi Ukuran Baju.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Celana.getText().toString()))
        {
            Toast.makeText(this, "Isi Ukuran Celana.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Sepatu.getText().toString()))
        {
            Toast.makeText(this, "Isi Ukuran Sepatu.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Hp.getText().toString()))
        {
            Toast.makeText(this, "Isi No HP.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(HpKeluarga.getText().toString()))
        {
            Toast.makeText(this, "Isi No HP Keluarga Terdekat.", Toast.LENGTH_SHORT).show();
        }

    }

    private void userInfoDisplay(EditText pendidikan, EditText alamat, EditText goldarah,
                                 EditText agama, EditText nonpwp, EditText baju, EditText celana,
                                 EditText sepatu, EditText hp, EditText hpKeluarga) {

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Karyawan")
                .child(Prevalent.currentOnlineUser.getNpp());
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("pendidikan").exists())
                    {


                        String pendidikan = dataSnapshot.child("pendidikan").getValue().toString();
                        String alamat = dataSnapshot.child("alamat").getValue().toString();
                        String goldarah = dataSnapshot.child("goldar").getValue().toString();
                        String agama = dataSnapshot.child("agama").getValue().toString();
                        String npwp = dataSnapshot.child("npwp").getValue().toString();
                        String baju = dataSnapshot.child("ukuran_baju").getValue().toString();
                        String celana = dataSnapshot.child("ukuran_celana").getValue().toString();
                        String sepatu = dataSnapshot.child("ukuran_sepatu").getValue().toString();
                        String hp = dataSnapshot.child("no_hp").getValue().toString();
                        String hpKeluarga = dataSnapshot.child("no_hpkeluarga").getValue().toString();

                        Pendidikan.setText(pendidikan);
                        Alamat.setText(alamat);
                        Goldarah.setText(goldarah);
                        Agama.setText(agama);
                        Nonpwp.setText(npwp);
                        Baju.setText(baju);
                        Celana.setText(celana);
                        Sepatu.setText(sepatu);
                        Hp.setText(hp);
                        HpKeluarga.setText(hpKeluarga);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void back(View view) {
        Intent i = new Intent(EditData.this,HomeScreen.class);
        startActivity(i);
    }


}