package com.example.puthagum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Paysts extends AppCompatActivity {
    EditText DOJ;
    private String ref1;
    DatabaseReference rootRef;
    private Button button1;
    private Button button2;
    private Calendar calendar;
    private int year, month, day;
    private RadioGroup radioGroup1;
    RadioButton radioButton1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paysts);
        DOJ = findViewById(R.id.edittext2);
        button1 = (Button) findViewById(R.id.on);
        button2 = (Button) findViewById(R.id.off);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        final String phno=getIntent().getStringExtra("Phno");
        final String des = getIntent().getStringExtra("desg");
        showDate(year, month+1, day);
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
                if ((radioGroup1.getCheckedRadioButtonId() == -1 || DOJ.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Fields are Empty", Toast.LENGTH_SHORT).show();
                } else {
                    rootRef = FirebaseDatabase.getInstance().getReference("Employer").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Job").child("+91" +phno).child("Date").child(DOJ.getText().toString());
                    rootRef.child("DOJ").setValue(DOJ.getText().toString());
                    rootRef.child("Psts").setValue(radioButton1.getText());
                    Intent intent = new Intent(Paysts.this, JobActivity.class);
                    intent.putExtra("desg", des);
                    intent.putExtra("Phno", phno);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Job Status Created", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(Paysts.this, JobActivity.class);
                intent.putExtra("desg", des);
                intent.putExtra("Phno", phno);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Job Status Creation Cancel",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "Select Job Date",
                Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        ref1=day+"-"+month+"-"+year;
        DOJ.setText(ref1);
    }
}