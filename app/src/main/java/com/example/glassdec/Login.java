package com.example.glassdec;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity
{
    TextView register,forget;
    EditText emaillogin, passlogin;
    Button login,patient;
    FirebaseAuth mAuth;
    String name;
    DatabaseReference reference;
   public static String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);
        forget=findViewById(R.id.forget);
        emaillogin = findViewById(R.id.email);
        passlogin = findViewById(R.id.password);
        login = findViewById(R.id.login);




        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(Login.this, RegisterUser.class);
                startActivity(intent);
            }
        });

        forget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                userLogin();
            }
        });

/*        patient.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),UserHome.class);
                startActivity(intent);
            }
        });*/
    }
    private void userLogin() {
        email = emaillogin.getText().toString().trim();
        String password = passlogin.getText().toString().trim();

        if(email.equals("admin") && password.equals("admin"))
        {
            Intent intent = new Intent(getApplicationContext(),admin_homepage.class);
            startActivity(intent);
        }

        if (email.isEmpty()) {
            emaillogin.setError("Please enter email id");
            emaillogin.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emaillogin.setError("Please provide valid email");
            emaillogin.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            passlogin.setError("Password is required");
            passlogin.requestFocus();
            return;
        }
        if (password.length() < 8) {
            passlogin.setError("Password should be greater than 8");
            passlogin.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                           /* reference = FirebaseDatabase.getInstance().getReference("Users");
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                                    assert user != null;
                                    String uid = user.getUid();
                                    name = dataSnapshot.child(uid).child("chooseAcc").getValue(String.class).trim();
                Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                                    if (name.equals("User")) {
                                        Intent driverintent = new Intent(getApplicationContext(), UserHome.class);
                                        startActivity(driverintent);
                                        finish();
                                    } else {
                                        Intent userintent = new Intent(getApplicationContext(), DriverHome.class);
                                        startActivity(userintent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {
                            Toast.makeText(getApplicationContext(), " Login Failed", Toast.LENGTH_SHORT).show();
                        }*/

                            Intent driverintent = new Intent(getApplicationContext(), UserHome.class);
                            startActivity(driverintent);
                            finish();

                        }
                    }
                });

    }

}