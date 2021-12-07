package com.example.travelguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.travelguru.Adapter.CityAdapter;
import com.example.travelguru.Model.City;
import com.example.travelguru.databinding.ActivityCityListBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CityList extends AppCompatActivity {

    ActivityCityListBinding binding;
    ArrayList<City> list;
    CityAdapter adapter;

    //
    FirebaseFirestore database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCityListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
}