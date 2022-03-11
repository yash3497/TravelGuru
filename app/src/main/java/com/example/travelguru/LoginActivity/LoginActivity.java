package com.example.travelguru.LoginActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.travelguru.CityList;
import com.example.travelguru.MainActivity;
import com.example.travelguru.R;
import com.example.travelguru.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    //
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signup.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
            startActivity(intent);
            finish();
        });

        //
        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        binding.okLogin.setOnClickListener(view -> {
            if(binding.inputEmailLogin.getText().toString().isEmpty()){
                binding.inputLayoutEmailLogin.setError("Enter your email");
                return;
            }
            if(binding.inputPassLogin.getText().toString().isEmpty()){
                binding.inputLayoutPassLogin.setError("Enter your password");
                return;
            }
            auth.signInWithEmailAndPassword(binding.inputEmailLogin.getText().toString(),binding.inputPassLogin.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser user = auth.getCurrentUser();
                                HashMap<String, String> map = new HashMap<>();
                                map.put("email",binding.inputEmailLogin.getText().toString());
                                map.put("password",binding.inputPassLogin.getText().toString());

                                assert user != null;
                                database.collection("user")
                                        .document(user.getUid())
                                        .set(map, SetOptions.merge());

                                Intent intent = new Intent(getApplicationContext(), CityList.class);
                                startActivity(intent);
                                finishAffinity();
                            }else {
                                Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(auth != null){
//            Intent intent = new Intent(getApplicationContext(), CityList.class);
//            startActivity(intent);
//            finishAffinity();
//        }
//    }
}