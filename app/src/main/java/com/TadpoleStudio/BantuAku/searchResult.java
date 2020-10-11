package com.TadpoleStudio.BantuAku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class searchResult extends AppCompatActivity {

    //widgets
    RecyclerView sView;

    //firebase
    private DatabaseReference BantuAkuDB;
    Button register;
    SearchView Search;
    Query searchquery;
    String textToSearch;

    //Variables
    private ArrayList<valueSetter> dataList;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result);

        sView = findViewById(R.id.searchView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        sView.setLayoutManager(gridLayoutManager);
        sView.setHasFixedSize(true);

        //firebase
        BantuAkuDB = FirebaseDatabase.getInstance().getReference("message");
        register = findViewById(R.id.RegisterButton);
        Search = findViewById(R.id.search);

        textToSearch = getIntent().getStringExtra("Wts");

        //ArrayList
        dataList = new ArrayList<>();

        //clearArrayList
        ClearAll();

        //Get Data Method
        GetDataFromFirebase();

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text)
            {
                textToSearch = text;
                GetDataFromFirebase();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        Search.setOnQueryTextListener(queryTextListener);

    }

    private void GetDataFromFirebase(){
        searchquery = BantuAkuDB.orderByChild("skill").startAt(textToSearch).endAt(textToSearch + "\uf8ff");
        searchquery.addValueEventListener(new ValueEventListener() {
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
                sView.setAdapter(recyclerAdapter);
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

}
