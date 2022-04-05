package com.example.glassdec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity
{
    FirebaseAuth mAuth;
    int code;
    TextView loginHere;
    RadioButton rb_user, rb_merchant;
    EditText hosp_name,phono,emailet,pass;
    Button registerUser,verify_email;
    String selectedtext;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth=FirebaseAuth.getInstance();
        hosp_name=findViewById(R.id.hospname);
        phono=findViewById(R.id.phno);
        emailet=findViewById(R.id.email);
        pass=findViewById(R.id.passwords);
        registerUser=findViewById(R.id.registeruser);
        rb_user = findViewById(R.id.userRB);
        rb_merchant = findViewById(R.id.merchantRB);
        loginHere=findViewById(R.id.login_here);
//        verify_email=findViewById(R.id.verifyemail);
        final FirebaseUser[] fuser = {mAuth.getCurrentUser()};

        loginHere.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(RegisterUser.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        registerUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (v.getId())
                {
                    case R.id.registeruser:
                        if (rb_merchant.isChecked() ||  rb_user.isChecked())
                        {
                            registerUser();
                        }
                        else {
                            Toast.makeText(RegisterUser.this, "Please Select Account" , Toast.LENGTH_SHORT).show();
                            break;
                        }
                }
            }
        });



    }


    private void registerUser() {
        final String email = emailet.getText().toString().trim();
        String password = pass.getText().toString().trim();
        final String hpname = hosp_name.getText().toString().trim();
        final String phnos = phono.getText().toString().trim();
        if (hpname.isEmpty()) {
            hosp_name.setError("Hospital name is required");
            hosp_name.requestFocus();
            return;
        }
        if (phnos.isEmpty()) {
            phono.setError("Phone Number is required");
            phono.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            emailet.setError("Email is required");
            emailet.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailet.setError("Please provide valid email");
            emailet.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Password is required");
            pass.requestFocus();
            return;
        }
        if (password.length() < 8) {
            pass.setError("Password should be greater than 8");
            pass.requestFocus();
            return;
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseUser fuser = mAuth.getCurrentUser();
                        fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(RegisterUser.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterUser.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        if (rb_merchant.isChecked()) {
                            selectedtext = rb_merchant.getText().toString();
                          //  Toast.makeText(RegisterUser.this, selectedtext, Toast.LENGTH_SHORT).show();
                            User user = new User(hpname, phnos, email, selectedtext);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//
                                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                        Intent mainintent = new Intent(RegisterUser.this, DriverHome.class);
                                        startActivity(mainintent);
                                        finish();
//
                                    } else {
                                        Toast.makeText(getApplicationContext(), " Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                        if (rb_user.isChecked()) {
                            selectedtext = rb_user.getText().toString();
                           // Toast.makeText(RegisterUser.this, selectedtext, Toast.LENGTH_SHORT).show();
                            User user = new User(hpname, phnos, email, selectedtext);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//
                                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                                        Intent mainintent = new Intent(RegisterUser.this, UserHome.class);
                                        startActivity(mainintent);
                                        finish();
//
                                    } else {
                                        Toast.makeText(getApplicationContext(), " Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "Email Already exits", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}