package com.example.travelguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.travelguru.Adapter.CityAdapter;
import com.example.travelguru.LoginActivity.LoginActivity;
import com.example.travelguru.Model.City;
import com.example.travelguru.databinding.ActivityCityListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CityList extends AppCompatActivity {

    ActivityCityListBinding binding;
    ArrayList<City> list;
    CityAdapter adapter;

    //
    FirebaseFirestore database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        setSupportActionBar(binding.toolbar);

        database = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        binding.cityRecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        datafetch();
    }

    private void datafetch(){
        database.collection("city")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list.clear();
                        List<DocumentSnapshot> snapshot = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot d:snapshot){
                            City city = new City();
                            city.setName(d.getString("name"));
                            city.setImage(d.getString("image"));
                            city.setDescription(d.getString("desc"));

                            list.add(city);
                        }
                        adapter = new CityAdapter(getApplicationContext(),list,CityList.this);
                        binding.cityRecyclerview.setAdapter(adapter);
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
            Intent intent = new Intent(CityList.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
            auth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}