package com.example.adhibeton;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class EditPassword extends AppCompatActivity {

    private EditText Npp, PassLama, PassBaru, UlangPass;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        CircularImageView profil = findViewById(R.id.profil);
        Picasso.get().load(Prevalent.currentOnlineUser.getProfil()).into(profil);

        Npp = (EditText) findViewById(R.id.npp);
        PassLama = (EditText) findViewById(R.id.passlama);
        PassBaru = (EditText) findViewById(R.id.passbaru);
        UlangPass = (EditText) findViewById(R.id.ulangipass);
        Button Submit = (Button) findViewById(R.id.button);

        userInfoDisplay(Npp, PassLama, PassBaru, UlangPass);


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
        userMap. put("npp", Npp.getText().toString());
        userMap. put("password", PassLama.getText().toString());
        userMap. put("password", PassBaru.getText().toString());

        ref.child(Prevalent.currentOnlineUser.getNpp()).updateChildren(userMap);


        Toast.makeText(EditPassword.this, "Password update successfully.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPassword.this, EditPassword.class));
        finish();
    }

    private void userInfoSaved() {
        if (TextUtils.isEmpty(PassBaru.getText().toString()))
        {
            Toast.makeText(this, "Masukkan Password Baru", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(UlangPass.getText().toString()))
        {
            Toast.makeText(this, "Ulangi Password Baru", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay(EditText npp, EditText passLama, EditText passBaru, EditText ulangPass) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("Karyawan")
                .child(Prevalent.currentOnlineUser.getNpp());
        UsersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.child("npp").exists())
                    {


                        String npp = dataSnapshot.child("npp").getValue().toString();
                        String password = dataSnapshot.child("password").getValue().toString();


                        Npp.setText(npp);
                        PassLama.setText(password);
                        PassBaru.setText("");
                        UlangPass.setText("");


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void back(View view) {
        Intent i = new Intent(EditPassword.this,HomeScreen.class);
        startActivity(i);
    }
}
