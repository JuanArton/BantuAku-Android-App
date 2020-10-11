package com.TadpoleStudio.BantuAku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NameInput extends AppCompatActivity{

    Button Next;
    TextView Skip, Welcome;
    EditText Nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_input);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Next = findViewById(R.id.nextButt);
        Skip = findViewById(R.id.skip);
        Nama = findViewById(R.id.inputNama);
        Welcome = findViewById(R.id.welcome);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NameInput.this, MainMenu.class);
                detail_profile sender = new detail_profile();
                sender.username = Nama.getText().toString();
                startActivity(intent);
            }
        });

        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NameInput.this, MainMenu.class);
                detail_profile sender = new detail_profile();
                sender.username = "";
                startActivity(intent);
            }
        });
    }
}
