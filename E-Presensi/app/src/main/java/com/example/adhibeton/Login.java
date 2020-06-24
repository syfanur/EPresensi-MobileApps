package com.example.adhibeton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cyd.awesome.material.AwesomeText;
import cyd.awesome.material.FontCharacterMaps;
import io.paperdb.Paper;

import static com.example.adhibeton.Prevalent.UserNppKey;

public class Login extends AppCompatActivity {

    private EditText InputNpp, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    private CheckBox chkBoxRememberMe;
    private String parentDbName = "Karyawan";
    AwesomeText ImgShowHidePassword;
    boolean pwd_status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = (Button) findViewById(R.id.btn_login);
        InputPassword = (EditText) findViewById(R.id.password);
        InputNpp = (EditText) findViewById(R.id.npp);
        loadingBar = new ProgressDialog(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_chkb);
        Paper.init(this);

        SharedPreferences sharedPreferences = getSharedPreferences(parentDbName, MODE_PRIVATE);
        String npp = sharedPreferences.getString("UserNpp", "");
        String pass = sharedPreferences.getString("UserPassword", "");

        InputNpp.setText(npp);
        InputPassword.setText(pass);

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });


        ImgShowHidePassword = (AwesomeText) findViewById(R.id.ImgShowPassword);

        ImgShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pwd_status) {
                    InputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    pwd_status = false;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY);
                    InputPassword.setSelection(InputPassword.length());
                } else {
                    InputPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    pwd_status = true;
                    ImgShowHidePassword.setMaterialDesignIcon(FontCharacterMaps.MaterialDesign.MD_VISIBILITY_OFF);
                    InputPassword.setSelection(InputPassword.length());
                }
            }
        });
    }

        private void LoginUser()
        {
            String npp = InputNpp.getText().toString();
            String password = InputPassword.getText().toString();

            if (TextUtils.isEmpty(npp))
            {
                Toast.makeText(this, "Please write your npp...", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(password))
            {
                Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
            }
            else
            {
                loadingBar.setTitle("Login Account");
                loadingBar.setMessage("Please wait, while we are checking the credentials.");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();


                AllowAccessToAccount(npp, password);
            }
        }



        private void AllowAccessToAccount(final String npp, final String password)
        {
            if(chkBoxRememberMe.isChecked())
            {
                Paper.book().write(UserNppKey, npp);
                Paper.book().write(Prevalent.UserPasswordKey, password);

                StoredDataUsingSharedPref(npp, password);
            }

            InputNpp.setText(npp);
            InputPassword.setText(password);


            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();


            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.child(parentDbName).child(npp).exists())
                    {
                        Karyawan usersData = dataSnapshot.child(parentDbName).child(npp).getValue(Karyawan.class);

                        if (usersData.getNpp().equals(npp))
                        {
                            if (usersData.getPassword().equals(password))
                            {
                                if (parentDbName.equals("Karyawan"))
                                {
                                    Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                                    loadingBar.dismiss();

                                    Intent intent = new Intent(Login.this, HomeScreen.class);
                                    Prevalent.currentOnlineUser = usersData;
                                    startActivity(intent);
                                }
                            }
                            else
                            {
                                loadingBar.dismiss();
                                Toast.makeText(Login.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else
                    {
                        Toast.makeText(Login.this, "Account with this " + npp + " number do not exists.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        private void StoredDataUsingSharedPref(String npp, String password) {
            SharedPreferences.Editor editor = getSharedPreferences(parentDbName,MODE_PRIVATE).edit();
            editor.putString("UserNpp",npp);
            editor.putString("UserPassword",password);
            editor.apply();
        }


}
