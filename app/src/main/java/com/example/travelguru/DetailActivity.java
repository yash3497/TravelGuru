package com.example.travelguru;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.travelguru.databinding.ActivityDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    FirebaseFirestore database;

    ArrayList<SlideModel> arrayList = new ArrayList<>();
    List<String> img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String collection = getIntent().getStringExtra("collection");
        String name = getIntent().getStringExtra("name");
        ImageSlider slider = binding.imageSlider;

        database = FirebaseFirestore.getInstance();
        database.collection(collection)
                .whereEqualTo("name", name)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot document : snapshots) {
                            binding.hotelName.setText(document.getString("name"));
                            binding.hotelDes.setText(document.getString("desc"));
                            String desc = binding.hotelDes.getText().toString().replace("\\n","\n");
                            binding.hotelDes.setText(desc);
                                    img = (List<String>) document.get("image");
                            GeoPoint geoPoint = document.getGeoPoint("map");
                            double lat = geoPoint.getLatitude();
                            double lang = geoPoint.getLongitude();

                            String url = document.getString("url");

                            binding.bookButton.setOnClickListener(view -> {
                                Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            });

                            binding.mapButton.setOnClickListener(view -> {

                                Uri gmmIntentUri = Uri.parse("geo:"+String.valueOf(lat)+","+String.valueOf(lang));
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);

                            });
                        }
                        for (int i = 0; i < Objects.requireNonNull(img).size(); i++) {
                            SlideModel s = new SlideModel(img.get(i), ScaleTypes.FIT);
                            arrayList.add(s);
                            Log.d("listdata",img.get(0).toString());
                        }
                        slider.setImageList(arrayList);
                    }
                });




    }


}