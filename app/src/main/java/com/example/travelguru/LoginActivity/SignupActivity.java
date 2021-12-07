package com.example.travelguru.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelguru.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;

    //
    FirebaseAuth auth;
    FirebaseFirestore database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.login.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        });

        

        //Signup Activity

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        binding.okSignup.setOnClickListener(view -> {

            if(binding.inputFirstnameSignup.getText().toString().isEmpty()){
                binding.inputLayoutFirstnameSignup.setError("Enter your first name");
                return;
            }
            if(binding.inputLastnameSignup.getText().toString().isEmpty()){
                binding.inputLayoutLastnameSignup.setError("Enter your last name");
                return;
            }
            if(binding.inputEmailSignup.getText().toString().isEmpty()){
                binding.inputLayoutEmailSignup.setError("Enter your email");
                return;
            }
            if(binding.inputPassSignup.getText().toString().isEmpty()){
                binding.inputLayoutPassSignup.setError("Enter your password");
                return;
            }if(binding.inputConfirmPassSignup.getText().toString().isEmpty()){
                binding.inputLayoutConfirmPassSignup.setError("Enter your confirm password");
                return;
            }
            if(binding.inputPassSignup.getText().toString().equals(binding.inputConfirmPassSignup.getText().toString())){

                auth.createUserWithEmailAndPassword(binding.inputEmailSignup.getText().toString(),binding.inputPassSignup.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user =auth.getCurrentUser();
                                    HashMap<String, String> map = new HashMap<>();
                                    map.put("name",binding.inputFirstnameSignup.getText().toString()+" "+binding.inputLastnameSignup.getText().toString());
                                    map.put("email",user.getEmail());
                                    map.put("password",binding.inputPassSignup.getText().toString());

                                    database.collection("user")
                                            .document(user.getUid())
                                            .set(map, SetOptions.merge());

                                    Toast.makeText(getApplicationContext(),"Account Created",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}