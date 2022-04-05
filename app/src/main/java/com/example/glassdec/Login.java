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
    Button login;
    FirebaseAuth mAuth;
    String name;
    DatabaseReference reference;
   public static String email;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        mAuth = FirebaseAuth.getInstance();
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
                finish();
            }
        });

        forget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this, ForgotPassword.class));
                finish();
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
    }

    private void userLogin() {
        email = emaillogin.getText().toString().trim();
        String password = passlogin.getText().toString().trim();
        if (email.equals("admin@gmail.com") && password.equals("admin"))
        {
            Intent driverintent = new Intent(getApplicationContext(),admin_homepage.class);
            startActivity(driverintent);
            finish();
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
        } else {
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getInstance().getCurrentUser();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_SHORT).show();
                                Intent driverintent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(driverintent);
                                finish();
//                                getLoginUID();
                            }
                            else {
                            Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                        }

//                                Intent driverintent = new Intent(getApplicationContext(), UserHome.class);
//                                startActivity(driverintent);
//                                finish();


                        }

                    });
        }
    }

    public void getLoginUID()
    {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

//                                    assert user != null;
//                                        String uid = null;
//                                        Toast.makeText(getApplicationContext(), ""+user, Toast.LENGTH_SHORT).show();
                if (user != null)
                {
                    String uid = user.getUid();
//                Toast.makeText(getApplicationContext(), uid, Toast.LENGTH_SHORT).show();
                    name = dataSnapshot.child(uid).child("chooseAcc").getValue(String.class).trim();

//                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
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
                else
                {
                    Toast.makeText(getApplicationContext(), " Loading", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(getApplicationContext(), " Failed to get data "+error, Toast.LENGTH_SHORT).show();
            }
        });

    }
}