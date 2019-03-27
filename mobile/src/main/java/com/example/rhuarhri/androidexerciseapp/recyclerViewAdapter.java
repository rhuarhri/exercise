package com.example.rhuarhri.androidexerciseapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rhuarhri.androidexerciseapp.externalDatabase.exercise;
import com.example.rhuarhri.androidexerciseapp.externalDatabase.storedExercises;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.WeightDBController;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBController;

import java.util.ArrayList;
import java.util.List;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.MyViewHolder> {

    List<String> FoundNames;
    List<String> FoundType;
    Context context;

    //Exercise list comes from fire store
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
        public TextView amountTXT;
        public Button plusBTN;
        public Button minusBTN;

        public MyViewHolder(View v)
        {
            super (v);

            exerciseTXT = (TextView) v.findViewById(R.id.exerciseTXT);
            typeIV = (ImageView) v.findViewById(R.id.exerciseTypeIV);
            amountTXT = (TextView) v.findViewById(R.id.numberTXT);
            plusBTN = (Button) v.findViewById(R.id.plusBTN);
            minusBTN = (Button) v.findViewById(R.id.minusBTN);
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
    public void onBindViewHolder(@NonNull final recyclerViewAdapter.MyViewHolder holder, int position) {

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

        holder.plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amount = Integer.parseInt(holder.amountTXT.getText().toString());

                amount++;

                holder.amountTXT.setText("" + amount);

                //adding the chosen exercise to the internal data base
                storedExercises addExercise = new storedExercises();

                addExercise.addToExerciseRoutine(FoundNames.get(position));


            }
        });

        holder.minusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int amount = Integer.parseInt(holder.amountTXT.getText().toString());

                if (amount > 0)
                {
                    amount--;

                    Data threadData = new Data.Builder().putString("function", "delete")
                            .putString("name", holder.exerciseTXT.getText().toString())
                            .build();

                    OneTimeWorkRequest RemoveExerciseDB =
                            new OneTimeWorkRequest.Builder(chosenExerciseDBController.class).setInputData(threadData).build();

                    WorkManager.getInstance().enqueue(RemoveExerciseDB);

                    holder.amountTXT.setText("" + amount);

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return FoundNames.size();
    }
}