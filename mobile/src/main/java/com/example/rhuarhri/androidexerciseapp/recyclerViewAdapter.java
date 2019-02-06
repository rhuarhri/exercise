package com.example.rhuarhri.androidexerciseapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder> {

    String[] exercises = {"exercise1", "exercise2", "exercise3", "exercise4", "exercise5", "exercise6",
            "exercise7", "exercise8", "exercise9", "exercise10"};

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView exerciseTXT;
        public ImageView typeIV;

        public MyViewHolder(View v)
        {
            super (v);

            exerciseTXT = (TextView) v.findViewById(R.id.exerciseTXT);
            typeIV = (ImageView) v.findViewById(R.id.exerciseTypeIV);
        }
    }

    @NonNull
    @Override
    public recyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        MyViewHolder vh = new MyViewHolder(v);



        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull recyclerViewAdapter.MyViewHolder holder, int position) {

        holder.exerciseTXT.setText("" + exercises[position]);
        holder.typeIV.setImageResource(R.drawable.arm);

    }

    @Override
    public int getItemCount() {
        return exercises.length;
    }
}
