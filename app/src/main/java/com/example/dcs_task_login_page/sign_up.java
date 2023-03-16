package com.example.dcs_task_login_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class sign_up extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText emai, pass;
    private Button signup;
    private ImageButton back;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        emai = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.sign__up);
        signup.setOnClickListener(this);
        back= (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
        progressBar=(ProgressBar) findViewById(R.id.pro);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign__up:
                registeruser();
                break;
            case R.id.back:
                startActivity(new Intent(sign_up.this, MainActivity.class));
                break;
        }
    }
    private void registeruser() {
        String email = emai.getText().toString().trim();
        String password = pass.getText().toString().trim();
        if(email.isEmpty()){
            emai.setError("Email is required");
            emai.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emai.setError("Email is not valid");
            emai.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if(password.length() < 8){
            pass.setError("Password should be 8 characters long");
            pass.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            user user= new user(email);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                progressBar.setVisibility(View.VISIBLE);
                                                Toast.makeText(sign_up.this, "User has been registered successfully",Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(sign_up.this, MainActivity.class));
                                                Toast.makeText(sign_up.this, "Log in again",Toast.LENGTH_SHORT).show();
                                            }else{
                                                progressBar.setVisibility(View.VISIBLE);
                                                Toast.makeText(sign_up.this,"Failed to register! Try again!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else{
                            progressBar.setVisibility(View.VISIBLE);
                            task.getException().getMessage();
                            Toast.makeText(sign_up.this, "User already exists.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}