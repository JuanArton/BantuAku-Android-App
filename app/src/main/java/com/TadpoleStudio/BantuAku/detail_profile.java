package com.TadpoleStudio.BantuAku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class detail_profile extends AppCompatActivity {

    ImageView profilepicture;
    TextView email, phone, whatsapp, skill, deskripsi, sellerName;
    Button waButton, mailButton, numButton;
    String Whatsapp, Pesan, Email, Phone;
    static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_profile);

        sellerName = findViewById(R.id.namaSeller);
        deskripsi = findViewById(R.id.desc);
        profilepicture = findViewById(R.id.sellerImage);
        skill = findViewById(R.id.skilText);
        whatsapp = findViewById(R.id.waNumber);
        email = findViewById(R.id.emailText);
        phone = findViewById(R.id.phoneNumber);
        waButton = findViewById(R.id.waButt);
        mailButton = findViewById(R.id.emailButt);
        numButton = findViewById(R.id.phoneButt);

        String nameseller = getIntent().getStringExtra("SellerName");
        String desc = getIntent().getStringExtra("Desc");
        String image = getIntent().getStringExtra("Image");
        String Skill = getIntent().getStringExtra("Skill");
        Whatsapp = getIntent().getStringExtra("Whatsapp");
        Email = getIntent().getStringExtra("Email");
        Phone = getIntent().getStringExtra("Phone");

        Glide.with(this)
                .load(image)
                .into(profilepicture);

        sellerName.setText(nameseller);
        deskripsi.setText(desc);
        skill.setText(Skill);
        whatsapp.setText(Whatsapp);
        email.setText(Email);
        phone.setText(Phone);

        waButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWA();
            }
        });

        mailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        numButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneDial();
            }
        });

    }

    private void openWA(){
        varCheck();
        String url = "https://api.whatsapp.com/send?phone=62" + Whatsapp + "&text=" + Pesan;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void varCheck(){

        if(username == ""){
            Pesan = "Hai, aku butuh bantuanmu. \n\n-BantuAku";
        }
        else{
            Pesan = "Hai, aku " + username + " butuh bantuanmu. \n\n-BantuAku";
        }
    }

    private void sendMail(){
        varCheck();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {Email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Bantu AKu");
        intent.putExtra(Intent.EXTRA_TEXT, Pesan);
        startActivity(Intent.createChooser(intent, "Ingin Hubungi Bantuan?"));
    }

    private void phoneDial(){

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + Phone));
        startActivity(intent);
    }

}
