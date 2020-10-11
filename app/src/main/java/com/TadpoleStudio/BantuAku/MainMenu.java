package com.TadpoleStudio.BantuAku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    //widgets
    RecyclerView recyclerView;

    //firebase
    private DatabaseReference BantuAkuDB;
    Button register;
    SearchView searchText;

    //Variables
    private ArrayList<valueSetter> dataList;
    private RecyclerAdapter recyclerAdapter;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        //firebase
        BantuAkuDB = FirebaseDatabase.getInstance().getReference();
        register = findViewById(R.id.RegisterButton);
        searchText = findViewById(R.id.searchbar);

        //ArrayList
        dataList = new ArrayList<>();

        //clearArrayList
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                Intent intent = new Intent(getApplicationContext(), searchResult.class);
                intent.putExtra("Wts", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        searchText.setOnQueryTextListener(queryTextListener);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

    }

    private void Register(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void GetDataFromFirebase(){

        Query query = BantuAkuDB.child("message");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ClearAll();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    valueSetter data = new valueSetter();

                    data.setImageUrl(snapshot.child("image").getValue().toString());
                    data.setSkill(snapshot.child("skill").getValue().toString());
                    data.setSeller(snapshot.child("nama").getValue().toString());
                    data.setDesc(snapshot.child("deskripsi").getValue().toString());
                    data.setWA(snapshot.child("whatsapp").getValue().toString());
                    data.setEmail(snapshot.child("email").getValue().toString());
                    data.setPhone(snapshot.child("phone").getValue().toString());

                    dataList.add(data);
                }

                recyclerAdapter = new RecyclerAdapter(getApplicationContext(), dataList);
                recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void ClearAll(){
        if (dataList !=null){
            dataList.clear();

            if(recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }

        }

        dataList = new ArrayList<>();

    }

    @Override
    public void onBackPressed()
    {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finish();
            finishAffinity();
            System.exit(0);
        }
        else { Toast.makeText(getBaseContext(), "Tekan sekali lagi untuk keluar", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();
    }

}
