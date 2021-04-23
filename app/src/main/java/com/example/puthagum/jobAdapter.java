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

import java.util.ArrayList;

public class jobAdapter extends RecyclerView.Adapter<jobAdapter.MyViewHolder> {

    Context context;
    ArrayList<job> Job;
    public jobAdapter(Context c,ArrayList<job> e){
        context=c;
        Job=e;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.person1,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.date.setText(Job.get(position).getDOJ());
        holder.status.setText(Job.get(position).getPsts());
        holder.payment.setText(Job.get(position).getPay());
    }

    @Override
    public int getItemCount() {
        return Job.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date,status,payment;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.doj);
            status=itemView.findViewById(R.id.sts);
            payment=itemView.findViewById(R.id.payme);
        }
    }

}
