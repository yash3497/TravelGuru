package com.example.travelguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.travelguru.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    float v=0;
    //
    FirebaseFirestore database;
    String city="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");

        binding.homeScroll.setTranslationX(300);
        binding.homeScroll.setAlpha(v);
        binding.homeScroll.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(900).start();


        //
        database = FirebaseFirestore.getInstance();

        getdata(name);

        binding.hotels.setOnClickListener(view -> {
            nextScreen(name+"Hotels");
        });
        binding.restaurants.setOnClickListener(view -> {
            nextScreen(name+"Restaurant");
        });
        binding.hospital.setOnClickListener(view -> {
            nextScreen(name+"Hospital");
        });
        binding.places.setOnClickListener(view -> {
            nextScreen(name+"Places");
        });
        binding.school.setOnClickListener(view -> {
            nextScreen(name+"School");
        });
    }
    private void getdata(String name){
        database.collection("city")
                .whereEqualTo("name",name)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                       List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                       for(DocumentSnapshot document : snapshots){
                           binding.homeCityName.setText(document.getString("name"));
                           city = document.getString("name");
                           binding.homeCityDes.setText(document.getString("desc"));
                           Glide.with(getApplicationContext()).load(document.getString("image")).into(binding.homeCityImg);
                       }
                    }
                });
    }
    private void nextScreen(String data){
        Intent intent = new Intent(getApplicationContext(),HotelsList.class);
        intent.putExtra("key",data);
        startActivity(intent);
    }
}