package com.anwar.splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private TextInputEditText Nomor,Password;

    //koneksi database realtime firebase
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myDataRef = database.getReferenceFromUrl("https://splash-d1db8-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //fungsi membuat fullscreen
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_login);

        Button btnMasuk = findViewById(R.id.btnMasuk);
        Button btnRegis = findViewById(R.id.btnRegis);

        Nomor = findViewById(R.id.logNomor);
        Password = findViewById(R.id.logPassword);

        //fungsi onclik
        btnRegis.setOnClickListener(view -> {
            startActivity(new Intent(Login.this,Registrasi.class));
            finish();
        });
        btnMasuk.setOnClickListener(view -> {
            //konsfersi data ke string
            final String nomorTxt = Objects.requireNonNull(Nomor.getText()).toString();
            final String passwordTxt = Objects.requireNonNull(Password.getText()).toString();
            if (nomorTxt.isEmpty()||passwordTxt.isEmpty()){
                Toast.makeText(Login.this,"Pastikan semua fild telah di isi",Toast.LENGTH_SHORT).show();
            }else {
                myDataRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //cek apakah user sudah terdaftar
                        if(snapshot.hasChild(nomorTxt)){
                            //jika data ada mk ambil data
                            final String getPassword = snapshot.child(nomorTxt).child("password").getValue(String.class);
                            if (Objects.equals(getPassword, passwordTxt)){
                                Toast.makeText(Login.this,"Selamat berselancar!!",Toast.LENGTH_SHORT).show();
                                //redirect ke dashboard
                                startActivity(new Intent(Login.this,Dashboard.class));
                                finish();
                            }else {
                                Toast.makeText(Login.this,"Silahkan periksa kembali password anda!!",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(Login.this,"Silahkan periksa kembali password dan nomor anda!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


        });
    }
}