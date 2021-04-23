package com.example.puthagum;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.MyViewHolder> {
    DatabaseReference rootRef, demoRef;
    Context context;
    ArrayList<Employee> employee;
    public EmployeeAdapter(Context c,ArrayList<Employee> e){
        context=c;
        employee=e;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.Phoneno.setText(employee.get(position).getPhoneNumber());
        rootRef = FirebaseDatabase.getInstance().getReference("Employee");

        // Database reference pointing to demo node
        demoRef = rootRef.child((String) holder.Phoneno.getText()).child("Name");
        demoRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                holder.Name.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return employee.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        Button button;
        TextView Name,Phoneno;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.Name);
            Phoneno=itemView.findViewById(R.id.Phno);
            button=itemView.findViewById(R.id.Button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),JobActivity.class);
                    intent.putExtra("Phno",Phoneno.getText());
                    intent.putExtra("desg", "Employer");
                    view.getContext().startActivity(intent);
                }
            });
        }
    }

}
