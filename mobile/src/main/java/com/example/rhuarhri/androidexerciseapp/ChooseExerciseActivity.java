package com.example.rhuarhri.androidexerciseapp;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.rhuarhri.androidexerciseapp.externalDatabase.storedExercises;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBAccess;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExercises;

import java.util.List;

public class ChooseExerciseActivity extends AppCompatActivity {
    RecyclerView exerciseList;
    RecyclerView.Adapter exerciseListAdapter;
    RecyclerView.LayoutManager exerciseListManager;

    Button startBTN;
    Button chestBTN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);



        exerciseList = (RecyclerView) findViewById(R.id.exerciseRV);

        setupRecyclerView();

        chestBTN = (Button) findViewById(R.id.chestBTN);
        startBTN = (Button) findViewById(R.id.startBTN);



        chestBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




            }
        });
        startBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startExerciseScreen = new Intent(getApplicationContext(), StartExerciseActivity.class);
                startActivity(startExerciseScreen);

            }
        });


    }

    private void setupRecyclerView()
    {
        //exerciseList.setHasFixedSize(true);

        exerciseListManager = new LinearLayoutManager(this);

        exerciseList.setLayoutManager(exerciseListManager);

        storedExercises AvailableExercises = new storedExercises();

        try {
            AvailableExercises.getAllExercises(exerciseList, getApplicationContext());
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Exercises not available check internet connection", Toast.LENGTH_SHORT).show();
        }

        /*
        exerciseListAdapter = new recyclerViewAdapter();
        exerciseList.setAdapter(exerciseListAdapter);*/
    }



}
