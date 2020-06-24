package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profil extends AppCompatActivity {
private Button signOut;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        TextView nama = findViewById(R.id.nama);
        TextView posisi = findViewById(R.id.posisi);

        nama.setText(Prevalent.currentOnlineUser.getNama());
        posisi.setText(Prevalent.currentOnlineUser.getPosisi());

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Profil.this, Login.class));
                    finish();
                }
            }
        };
//
        signOut = (Button) findViewById(R.id.logout);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Profil.this, "Logout Berhasil", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Profil.this, Login.class));
                signOut();
            }
        });
    }
//

    //sign out method
    public void signOut() {
        auth.signOut();
    }

    public void editpass(View view) {
        Intent a = new Intent(Profil.this, EditPassword.class);
        startActivity(a);
    }

    public void editdata(View view) {
        Intent b = new Intent(Profil.this, EditData.class);
        startActivity(b);
    }


}
