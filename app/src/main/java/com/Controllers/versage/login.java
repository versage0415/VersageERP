package com.Controllers.versage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    Button btnlogin;
    Spinner spinner;
    EditText EtMail,PassId;
    TextView tvregisterLink;
    ProgressBar progressBarLogin;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener  mAuthStateListner;

    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        EtMail=(EditText)findViewById(R.id.EtMail);
        PassId=(EditText)findViewById(R.id.PassId);
        btnlogin=(Button)findViewById(R.id.btnlogin);
        //spinner=(Spinner)findViewById(R.id.spinner);
        tvregisterLink=(TextView)findViewById(R.id.tvRegisterLink);
        progressBarLogin=(ProgressBar)findViewById(R.id.progressBarLogin);

        mAuth = FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email=EtMail.getText().toString().trim();
                password=PassId.getText().toString().trim();

                if(email.isEmpty()){
                    EtMail.setError("Email is required");
                    System.out.println(email);
                    return;
                }
                if(password.isEmpty()){
                    PassId.setError("password is required");
                    System.out.println(password);
                    return;
                }
                if(password.length()<=6){
                    PassId.setError("password muss be >=6 characters");

                }
                 if(email.isEmpty()&&password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"All Fields are empty",Toast.LENGTH_LONG).show();
                }
                progressBarLogin.setVisibility(View.VISIBLE);
               // else if(!(username.isEmpty()&&password.isEmpty())){
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBarLogin.setVisibility(View.GONE);
                            if(!task.isSuccessful()){
                                Toast.makeText(login.this,"Login Unsuccessful,please try Again" +" "+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                            else if(task.isSuccessful()){
                                FirebaseUser user=mAuth.getCurrentUser();
                                Toast.makeText(login.this,"Login successful!",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(login.this,Menu.class);
                                startActivity(intent);
                            }
                        }});
                }
        });
        tvregisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(login.this,register.class);
                startActivity(intent);
            }
        });
    }
    /*protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }*/


}