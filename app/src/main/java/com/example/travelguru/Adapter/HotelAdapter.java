package com.example.travelguru.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelguru.DetailActivity;
import com.example.travelguru.MainActivity;
import com.example.travelguru.Model.City;
import com.example.travelguru.R;
import com.example.travelguru.databinding.CityCardBinding;

import java.util.ArrayList;


public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder>{
    Context context;
    ArrayList<City> list;
    Activity activity;
    String collection;

    public HotelAdapter(Context context, ArrayList<City> list, Activity activity, String collection) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        this.collection = collection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.city_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = list.get(position);

        Glide.with(context).load(city.getImage()).into(holder.binding.cityImage);
        holder.binding.cityName.setText(city.getName());
        holder.binding.cityDesc.setText(city.getDescription());
        holder.binding.cityImage.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("name",city.getName());
            intent.putExtra("collection",collection);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder{
        CityCardBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CityCardBinding.bind(itemView);
        }
    }
}
