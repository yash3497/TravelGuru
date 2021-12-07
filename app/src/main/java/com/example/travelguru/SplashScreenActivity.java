package com.example.travelguru;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.travelguru.LoginActivity.LoginActivity;
import com.example.travelguru.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Handler().postDelayed(() -> {
            Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
            Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(),
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle();
            startActivity(intent,bundle);
            finish();
        }, 1000);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}