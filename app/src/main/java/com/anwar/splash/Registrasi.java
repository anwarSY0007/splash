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

public class Registrasi extends AppCompatActivity {

    private Button btnMasuk,btnRegis;
    private TextInputEditText Nama,Username,Email,Nomor,Pass,PassKonfir;
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

        setContentView(R.layout.activity_registrasi);

        btnMasuk = findViewById(R.id.btnMasuk);
        btnRegis = findViewById(R.id.btnRegis);

        Nama = findViewById(R.id.nama);
        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Nomor = findViewById(R.id.nomor);
        Pass = findViewById(R.id.pass);
        PassKonfir = findViewById(R.id.passKonfir);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //konver data ke string
                final String namaTxt = Nama.getText().toString();
                final String userNameTxt = Username.getText().toString();
                final String  emailTxt = Email.getText().toString();
                final String  nomorTxt = Nomor.getText().toString();
                final String passTxt = Pass.getText().toString();
                final String  passKonfTxt = PassKonfir.getText().toString();
                //cek semua input text telah terisi
                if (namaTxt.isEmpty()||userNameTxt.isEmpty()||emailTxt.isEmpty()||nomorTxt.isEmpty()||passTxt.isEmpty()){
                    Toast.makeText(Registrasi.this,"Pastikan semua fild telah di isi",Toast.LENGTH_SHORT).show();
                }else if (!passTxt.equals(passKonfTxt)){
                    //password tidak sama
                    Toast.makeText(Registrasi.this,"Pastikan password harus sama",Toast.LENGTH_SHORT).show();
                }else {
                    myDataRef.child("user").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //cek duplikasi nomor
                            if (snapshot.hasChild(nomorTxt)){
                                Toast.makeText(Registrasi.this,"Nomor sudah terdaftar!!",Toast.LENGTH_SHORT).show();
                            }else {
                                //kirim data menuju database dengan menggunakan primaryKey nomor sebagai uniq
                                myDataRef.child("users").child(nomorTxt).child("nama").setValue(namaTxt);
                                myDataRef.child("users").child(nomorTxt).child("username").setValue(userNameTxt);
                                myDataRef.child("users").child(nomorTxt).child("email").setValue(emailTxt);
                                myDataRef.child("users").child(nomorTxt).child("password").setValue(passTxt);

                                //munculkan notif
                                Toast.makeText(Registrasi.this,"Data anda berhasil di simpan",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Registrasi.this,Login.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });

        //fungsi kembali ke activity login
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registrasi.this,Login.class));
                finish();
            }
        });
    }
}