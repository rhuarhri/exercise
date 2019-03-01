package com.example.rhuarhri.androidexerciseapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhuarhri.androidexerciseapp.externalDatabase.exercise;

import java.util.ArrayList;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder> {



    List<String> FoundNames;
    List<String> FoundType;
    Context context;


    public recyclerViewAdapter (List<exercise> ExerciseList, Context appContext)
    {
        List<exercise> allExercises = ExerciseList;

        FoundNames = new ArrayList<String>();
        FoundType = new ArrayList<String>();

        context = appContext;

        for (int i = 0; i < allExercises.size(); i++)
        {
            FoundNames.add(allExercises.get(i).getName());
            FoundType.add(allExercises.get(i).getType());
        }

    }


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

        holder.exerciseTXT.setText("" + FoundNames.get(position));

        if (FoundType.get(position) == "arm") {
            holder.typeIV.setImageResource(R.drawable.arm);
        }
        else if (FoundType.get(position) == "leg")
        {
            holder.typeIV.setImageResource(R.drawable.leg);
        }
        else
        {
            holder.typeIV.setImageResource(R.drawable.chest);
        }

    }

    @Override
    public int getItemCount() {
        return FoundNames.size();
    }
}
