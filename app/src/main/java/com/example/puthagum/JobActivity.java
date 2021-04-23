package com.example.puthagum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button button1, button2;
    DatabaseReference ref;
    RecyclerView recyclerView;
    DatabaseReference mbase;
    ArrayList<job> list;
    jobAdapter adapter;
    private String  Employee="Employee";
    private String  Employer="Employer";
    DatabaseReference rootRef, demoRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        final String ID = getIntent().getStringExtra("Phno");
        final String des = getIntent().getStringExtra("desg");
        firebaseAuth = FirebaseAuth.getInstance();
        button1 = findViewById(R.id.SignOut);
        button2 = findViewById(R.id.edit);
        if(des.equals(Employee)){
            button2.setVisibility(View.INVISIBLE);
        }
        if(des.equals(Employer)){
            button2.setVisibility(View.VISIBLE);
        }
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Intent intent = new Intent(JobActivity.this, Paysts.class);
                intent.putExtra("desg", des);
                intent.putExtra("Phno", ID);
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                Intent intent = new Intent(JobActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Sign Out", Toast.LENGTH_SHORT).show();
            }
        });
        rootRef = FirebaseDatabase.getInstance().getReference("Employee");
        final String as="NoData";
        // Database reference pointing to demo node
        demoRef = rootRef.child(ID).child("Employer");
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value==as) {
                    Toast.makeText(getApplicationContext(), value+":No job Assigned", Toast.LENGTH_SHORT).show();
                }
                else{
                    mbase = FirebaseDatabase.getInstance().getReference("Employer").child(value).child("Job").child(ID).child("Date");
                    recyclerView = findViewById(R.id.recycler);

                    // To display the Recycler view linearly
                    recyclerView.setLayoutManager(new LinearLayoutManager(JobActivity.this));
                    list = new ArrayList<job>();

                    mbase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                job e = snapshot1.getValue(job.class);
                                list.add(e);
                            }
                            adapter = new jobAdapter(JobActivity.this, list);
                            recyclerView.setAdapter(adapter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "ERROR:" + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JobActivity.this, "Error fetching data", Toast.LENGTH_LONG).show();
            }
        });

    }
}