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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText signInEmailEditText,signInPasswordEditText;
    private TextView signUpTextView;
    private Button signInButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Online Voting System");
        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.sign_inProgressbarId);
        signInEmailEditText=findViewById(R.id.Sign_InEmailID);
        signInPasswordEditText=findViewById(R.id.sign_inPasswordID);
        signUpTextView=findViewById(R.id.sign_upTextID);
        signInButton=findViewById(R.id.sign_inButtonId);
        signUpTextView.setOnClickListener(this);
        signInButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
switch (view.getId())
{
    case R.id.sign_inButtonId:
        userSignIN();
        break;


    case R.id.sign_upTextID:
        Intent intent=new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
        break;
}

    }

    private void userSignIN() {
        String email=signInEmailEditText.getText().toString().trim();
        final String password=signInPasswordEditText.getText().toString().trim();
        if(email.isEmpty())
        {
           signInEmailEditText.setError("Enter an Email Address");
           signInEmailEditText.requestFocus();
           return;
        }
        if(!Patterns.EMAIL_ADDRESS .matcher(email).matches() )
        {
            signInEmailEditText.setError("Enter a valid  Email Address");
            signInEmailEditText.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
           signInPasswordEditText.setError("Enter a password");
           signInPasswordEditText.requestFocus();
           return;
        }
        if(password.length()<6)
        {
            signInPasswordEditText.setError("Minimum length of password should be 6");
            signInPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              progressBar.setVisibility(View.GONE);

              if(task.isSuccessful())
              {
                  Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);
                  finish();
              }
              else if(!Patterns.EMAIL_ADDRESS.matcher(password).matches() )
              {
                  Toast.makeText(getApplicationContext(),"Password is wrong",Toast.LENGTH_SHORT).show();
              }
              else{
                  Toast.makeText(getApplicationContext(),"User Id is Deleted",Toast.LENGTH_SHORT).show();
              }
          }
      });

    }
}
