package com.TadpoleStudio.BantuAku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Register extends AppCompatActivity {

    private static final int pick = 1;
    ImageView chooser;
    EditText dataWA, dataMail, dataDesc, dataNama, dataNum, dataServ;
    Button send;
    String WA, Mail, Number, Desc, Nama, Pesan, path ;
    Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        send = findViewById(R.id.Send);
        chooser = findViewById(R.id.pickedImage);
        dataWA = findViewById(R.id.dataWhatsapp);
        dataMail = findViewById(R.id.dataEmail);
        dataDesc = findViewById(R.id.dataDeskripsi);
        dataNama = findViewById(R.id.dataName);
        dataNum = findViewById(R.id.dataNumber);
        dataServ = findViewById(R.id.dataLayanan);

        send.setEnabled(false);

        chooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, pick);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Nama = dataNama.getText().toString();
                Desc = dataDesc.getText().toString();
                Mail = dataMail.getText().toString();
                WA = dataWA.getText().toString();
                Number = dataNum.getText().toString();
                Pesan = "Nama : " + Nama +
                        "\nDeskripsi : " + Desc +
                        "\nMail : " + Mail +
                        "\nWhatsapp : " + WA +
                        "\nNumber : " + Number;
                Kirim();
            }
        });
    }

    protected void Kirim(){
        String mEmail = "tadpolestdev@gmail.com";
        String mSubject = "Service Request";
        String mMessage = Pesan;


        JavaMailAPI javaMailAPI = new JavaMailAPI(this, mEmail, mSubject, mMessage, path);
        javaMailAPI.execute();

        Intent intent = new Intent(Register.this, backtomain.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch(requestCode){
                case pick:
                    imageUri = data.getData();
                    path = imageUri.toString();
                    Log.d("ADebugTag", "Value: " + path);
                    Glide.with(this).load(imageUri).into(chooser);
                    send.setEnabled(true);
                    send.setBackgroundColor(Color.parseColor("#fbaf5d"));
                    send.setTextColor(Color.parseColor("#ffffff"));
                    break;
            }

        }
    }

}
