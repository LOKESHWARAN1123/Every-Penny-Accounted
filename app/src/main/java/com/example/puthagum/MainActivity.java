package com.example.puthagum;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMobile;
    Button btnContinue;
    String f;
    Button Signup;
    private RadioGroup radioGroup;
    RadioButton radioButton;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextMobile = findViewById(R.id.editTextMobile);
        btnContinue = findViewById(R.id.buttonContinue);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Signup=findViewById(R.id.Createacc);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });
        radioGroup = (RadioGroup)findViewById(R.id.groupradio);
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        radioButton = (RadioButton)group.findViewById(checkedId);
                    }
                });
            btnContinue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String mobileNo = editTextMobile.getText().toString().trim();
                    if (mobileNo.length() != 10) {
                        editTextMobile.setError("Enter a valid mobile");
                        editTextMobile.requestFocus();
                        return;
                    }else {
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference userNameRef = rootRef.child((String) radioButton.getText()).child("+91"+mobileNo).child("PhoneNumber");
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    Intent intent = new Intent(MainActivity.this, VerifyPhoneActivity.class);
                                    intent.putExtra("mobile", mobileNo);
                                    intent.putExtra("desg", radioButton.getText());
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Please Create an account before login" + dataSnapshot.exists(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError error) {
                                Toast.makeText(getApplicationContext(),"ERROR:"+error,Toast.LENGTH_SHORT).show();
                            }
                        };
                        userNameRef.addListenerForSingleValueEvent(eventListener);
                    }

                }
            });
    }
}