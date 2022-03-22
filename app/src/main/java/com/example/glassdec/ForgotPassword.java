package com.example.glassdec;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity
{
    EditText resetemail;
    Button reset;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        resetemail=findViewById(R.id.forgetemail);
        reset=findViewById(R.id.reset);
        auth=FirebaseAuth.getInstance();

        reset.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                resetPassword();
            }
        });
    }

    private void resetPassword()
    {
        String femail=resetemail.getText().toString().trim();
        if (femail.isEmpty())
        {
            resetemail.setError("Please enter email id");
            resetemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(femail).matches())
        {
            resetemail.setError("Please provide valid email");
            resetemail.requestFocus();
            return;
        }
        auth.sendPasswordResetEmail(femail).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(ForgotPassword.this,"Check your Email to Reset Password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ForgotPassword.this,"Try Again ! Something wrong happened",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}