package com.example.puthagum;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    private EditText edittext1;
    private EditText edittext2;
    private EditText edittext3;
    private Button button1;
    private Button button2;
    RadioButton radioButton;
    RadioButton radioButton1;
    DatabaseReference rootRef,demoref;
    private FirebaseAuth mAuth;
    private RadioGroup radioGroup;
    private RadioGroup radioGroup1;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        edittext1 = (EditText) findViewById(R.id.edittext1);
        edittext2 = (EditText) findViewById(R.id.edittext2);
        edittext3 = (EditText) findViewById(R.id.edittext3);
        button1 = (Button) findViewById(R.id.on);
        button2 = (Button) findViewById(R.id.off);
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
        radioGroup1 = (RadioGroup)findViewById(R.id.groupradio1);
        radioGroup1.clearCheck();
        radioGroup1.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId)
                    {
                        radioButton1 = (RadioButton)group.findViewById(checkedId);
                    }
                });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if ((edittext1.getText().toString().equals("") || edittext2.getText().toString().equals("")) || edittext3.getText().toString().equals("")||radioGroup.getCheckedRadioButtonId() == -1||radioGroup1.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(),"Fields are Empty",Toast.LENGTH_SHORT).show();
                }
                else if (edittext3.getText().toString().trim().length() != 10){
                    Toast.makeText(getApplicationContext(),"Enter Vaild Number",Toast.LENGTH_SHORT).show();
                }
                else{
                    new CountDownTimer(1000,1000){
                        @Override
                        public void onTick(long l) {
                        }

                        @Override
                        public void onFinish() {
                            rootRef = FirebaseDatabase.getInstance().getReference((String) radioButton.getText());
                            demoref = rootRef.child("+91"+edittext3.getText().toString());
                            demoref.child("Name").setValue(edittext1.getText().toString());
                            demoref.child("Age").setValue(edittext2.getText().toString());
                            demoref.child("PhoneNumber").setValue("+91" +edittext3.getText().toString());
                            demoref.child("Designation").setValue(radioButton.getText());
                            demoref.child("Gender").setValue(radioButton1.getText());
                            demoref.child("Employer").setValue("NoData");
                            Toast.makeText(getApplicationContext(),"Successful Registration",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Signup.this, VerifyPhoneActivity.class);
                            intent.putExtra("mobile",edittext3.getText().toString());
                            intent.putExtra("desg",radioButton.getText());
                            startActivity(intent);
                        }
                    }.start();
                }
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Registration Cancel",Toast.LENGTH_SHORT).show();
            }
        });
    }
}