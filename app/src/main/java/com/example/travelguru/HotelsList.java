package com.example.travelguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.travelguru.Adapter.CityAdapter;
import com.example.travelguru.Adapter.HotelAdapter;
import com.example.travelguru.LoginActivity.LoginActivity;
import com.example.travelguru.Model.City;
import com.example.travelguru.databinding.ActivityHotelsListBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HotelsList extends AppCompatActivity {
    ActivityHotelsListBinding binding;
    ArrayList<City> list;
    HotelAdapter adapter;

    //
    FirebaseFirestore database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        String location = getIntent().getStringExtra("key");

        setSupportActionBar(binding.toolbar);

        database = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        binding.hotelRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        datafetch(location);
        binding.toolbar.setTitle(location);
    }

    private void datafetch(String location){
        database.collection(location)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list.clear();
                        List<DocumentSnapshot> snapshot = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:snapshot){
                            City city = new City();
                            city.setName(d.getString("name"));
                            city.setImage(d.getString("profile"));
                            city.setDescription(d.getString("desc"));

                            list.add(city);
                        }
                        adapter = new HotelAdapter(getApplicationContext(),list,HotelsList.this,location);
                        binding.hotelRecyclerview.setAdapter(adapter);
                    }
                });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu instanceof MenuBuilder) {
            ((MenuBuilder) menu).setOptionalIconsVisible(true);
        }
        getMenuInflater().inflate(R.menu.citymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            Intent intent = new Intent(HotelsList.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
            auth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}