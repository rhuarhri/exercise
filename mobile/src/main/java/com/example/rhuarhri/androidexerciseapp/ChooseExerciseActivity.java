package com.example.rhuarhri.androidexerciseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class ChooseExerciseActivity extends AppCompatActivity {
    RecyclerView exerciseList;
    RecyclerView.Adapter exerciseListAdapter;
    RecyclerView.LayoutManager exerciseListManager;

    Button startBTN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_exercise);



        exerciseList = (RecyclerView) findViewById(R.id.exerciseRV);

        setupRecyclerView();

        startBTN = (Button) findViewById(R.id.startBTN);


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
        exerciseList.setHasFixedSize(true);

        exerciseListManager = new LinearLayoutManager(this);

        exerciseList.setLayoutManager(exerciseListManager);

        exerciseListAdapter = new recyclerViewAdapter();
        exerciseList.setAdapter(exerciseListAdapter);
    }



}
