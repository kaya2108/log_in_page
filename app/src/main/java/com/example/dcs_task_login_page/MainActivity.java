package com.example.dcs_task_login_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button signup,login,ch_pass;
    private EditText email_l,pass_l;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup=(Button) findViewById(R.id.sign_up);
        signup.setOnClickListener(this);

        login=(Button) findViewById(R.id.login);
        login.setOnClickListener(this);

        ch_pass=(Button) findViewById(R.id.change_pass);
        ch_pass.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        email_l=findViewById(R.id.email);
        pass_l=findViewById(R.id.password);
        progressBar=(ProgressBar) findViewById(R.id.pro);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.sign_up:
                startActivity(new Intent(this,sign_up.class));
                break;

            case R.id.login:
                loginuser();
                break;

            case R.id.change_pass:
                Toast.makeText(getApplicationContext(), "Feature not available yet", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void loginuser() {
        String email=email_l.getText().toString().trim();
        String password=pass_l.getText().toString().trim();

        if(email.isEmpty()){
            email_l.setError("Email is required");
            email_l.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pass_l.setError("Password is required");
            pass_l.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,MainActivity2.class));
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Wrong email or password", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}