package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton partyButton;
    private Button voteButton;
    private TextView resultTextView;
    private FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
this.setTitle("Online Voting System");
mAuth =FirebaseAuth.getInstance();
firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
databaseReference= FirebaseDatabase.getInstance().getReference("PartyNames");
radioGroup=findViewById(R.id.radioGroupId);
voteButton=findViewById(R.id.ButtonId);
resultTextView=findViewById(R.id.resultId);


        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveVoteData();
                Intent intent=new Intent(ProfileActivity.this,SignUpActivity.class);
                startActivity(intent);
                firebaseUser.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"User is deleted",Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
    public void saveVoteData()
    {
        int selectedId= radioGroup.getCheckedRadioButtonId();
        partyButton=(RadioButton) findViewById(selectedId);
        String name=partyButton.getText().toString().trim();

        String key=databaseReference.push().getKey();

        PartyName partyname=new PartyName(name);

        databaseReference.child(key).setValue(partyname);
        Toast.makeText(getApplicationContext(), "Party name is added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_layout,menu);
        final boolean b = super.onCreateOptionsMenu(menu);
        return b;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.signOutId)
        {
            FirebaseAuth.getInstance().signOut();
            finish();
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

