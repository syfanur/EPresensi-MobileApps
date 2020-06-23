package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class detailizin extends AppCompatActivity {

    private ImageView mbukti;
    TextView mjenis, mstart, makhir, mketerangan;
    private String id = "";
    ImageView imageView;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailizin);

        id = getIntent().getStringExtra("pid");

        mjenis = findViewById(R.id.jenisizin);
        mstart = findViewById(R.id.mtanggal);
        makhir = findViewById(R.id.makhir);
        mbukti = findViewById(R.id.buktiizin);
        mketerangan = findViewById(R.id.keterangan);
        getDetailIzin(id);

        ActivityCompat.requestPermissions(detailizin.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        imageView = (ImageView) findViewById(R.id.buktiizin);
        save = (Button) findViewById(R.id.btnsave);
        ActivityCompat.requestPermissions(detailizin.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagesavetomyphonegallery();
            }

        });
    }

    private void imagesavetomyphonegallery() {
        ImageView img = (ImageView) findViewById(R.id.buktiizin);

        BitmapDrawable draw = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = draw.getBitmap();

        FileOutputStream outStream = null;
        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/SaveImages");
        dir.mkdirs();
        String fileName = String.format("%d.jpg", System.currentTimeMillis());
        File outFile = new File(dir, fileName);
        try {
            outStream = new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
        try {
            Toast.makeText(getApplicationContext(),"Image Saved", Toast.LENGTH_LONG).show();
            outStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDetailIzin(String id) {
        DatabaseReference izinRef = FirebaseDatabase.getInstance().getReference().child("Perizinan");

        izinRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Izin izin = dataSnapshot.getValue(Izin.class);

                    Picasso.get().load(izin.getBukti()).into(mbukti);
                    mjenis.setText(" : "+izin.getJenis());
                    mstart.setText(" : "+izin.getStart());
                    makhir.setText(" : "+izin.getEnd());
                    mketerangan.setText(" : "+izin.getKeterangan());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void back(View view) {
        Intent i = new Intent(detailizin.this,IzinProses.class);
        startActivity(i);
    }

    }
