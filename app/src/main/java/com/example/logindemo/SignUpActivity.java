package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

private EditText signUpEmailEditText,signUpPasswordEditText,signUpNameEditText,signUpAgeEditText;
private TextView SignInText;
private Button SignUpButton;
private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        this.setTitle("Online Voting System");

        mAuth = FirebaseAuth.getInstance();
        signUpAgeEditText=findViewById(R.id.signUpAgeID);
        signUpNameEditText=findViewById(R.id.sign_upNameID);
        progressBar=findViewById(R.id.signUpProgressbarId);
        signUpEmailEditText=findViewById(R.id.sign_upEmailID);
        signUpPasswordEditText=findViewById(R.id.sign_upPasswordID);
        SignInText=findViewById(R.id.signInTextID);
        SignUpButton=findViewById(R.id.signUpButtonId);

        SignUpButton.setOnClickListener(this);
        SignInText.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.signUpButtonId:
                userRegister();

                break;


            case R.id.signInTextID:
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void userRegister() {
        String name=signUpNameEditText.getText().toString();
        String email= signUpEmailEditText.getText().toString().trim();
        String password=signUpPasswordEditText.getText().toString().trim();
        String age=signUpAgeEditText.getText().toString().trim();

        if(name.isEmpty())
        {
            signUpNameEditText.setError("Enter Name");
            signUpNameEditText.requestFocus();
            return;
        }
        if(name.isEmpty())
        {
            signUpNameEditText.setError("Enter age");
            signUpNameEditText.requestFocus();
            return;
        }
        if(Integer.valueOf(age)<18)
        {
            signUpNameEditText.setError("You can not register in the System");
            signUpNameEditText.requestFocus();
            return;
        }

        if(email.isEmpty())
        {
            signUpEmailEditText.setError("Enter an Email Address");
            signUpEmailEditText.requestFocus();
            return;
        }
        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signUpEmailEditText.setError("Enter a valid Email Address");
            signUpEmailEditText.requestFocus();
            return;

        }

        if(password.isEmpty())
        {
           signUpPasswordEditText.setError("Enter a password");
           signUpPasswordEditText.requestFocus();
           return;
        }
        if(password.length()<6)
        {
            signUpPasswordEditText.setError("Minimum length of password should be 6");
            signUpPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()) {
                   finish();
                   Intent intent=new Intent(getApplicationContext(),IntermidiateActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent);

                } else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplication(),"User is already registered",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplication(),"Error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }
}
