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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    Button btnRegister;
    //Spinner spinner2;
    EditText etPhone, etEmail, etUsername, etPassword;
    TextView tvloginLink;
    ProgressBar progressBarRegister;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener  mAuthStateListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etUsername=(EditText)findViewById(R.id.etUsername);
        etPhone=(EditText)findViewById(R.id.etPhoneNumber);
        etPassword=(EditText)findViewById(R.id.etPassword);
        btnRegister=(Button)findViewById(R.id.btnRegister);
        tvloginLink=(TextView)findViewById(R.id.tvloginLink);
        progressBarRegister=(ProgressBar)findViewById(R.id.progressBarRegister);
        mFirebaseAuth=FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser()!=null){
            Intent intent=new Intent(register.this,Admin.class);
            startActivity(intent);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final  String mail=etEmail.getText().toString().trim();
                 String password=etPassword.getText().toString().trim();
                final String username=etUsername.getText().toString().trim();
                final String phone=etPhone.getText().toString().trim();
                 if(TextUtils.isEmpty(password)){
                     etPassword.setError("password is required");
                     return;
                 }
                 if(password.length()<6){
                     etPassword.setError("the password muss be longer than 6 letters");
                     return;
                 }
                 if(TextUtils.isEmpty(mail)){
                    etEmail.setError("Email is required");
                    return;
                }
                progressBarRegister.setVisibility(View.VISIBLE);
                    mFirebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressBarRegister.setVisibility(View.GONE);
                            if(!task.isSuccessful()){
                                Toast.makeText(register.this,"Registration Unsuccessful,please try Again"+""+ task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                            else if(task.isSuccessful()){
                                Users user=new Users(username,mail,phone);
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(register.this,"savely stored in Database",Toast.LENGTH_LONG).show();
                                                } else{
                                                    Toast.makeText(register.this,"Error!"+""+task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });
                                Intent intent=new Intent(register.this,Admin.class);
                                startActivity(intent);
                            }
                        }
                    });
            }
        });
        tvloginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(register.this,login.class);
                startActivity(intent);
            }
        });
    }
}