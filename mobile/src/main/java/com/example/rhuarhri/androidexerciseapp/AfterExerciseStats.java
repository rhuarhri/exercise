package com.example.rhuarhri.androidexerciseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AfterExerciseStatsActivity extends AppCompatActivity {

    Button statsBTN;
    Button homeBTN;

    appTimer recordTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_exercise_stats);
        statsBTN = (Button) findViewById(R.id.statsBTN);

        statsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overallStatsScreen = new Intent(getApplicationContext(), OverStatsActivity.class);

                startActivity(overallStatsScreen);
            }
        });

        homeBTN = (Button) findViewById(R.id.homeBTN);

        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);

                startActivity(homeScreen);
            }
        });

        recordTime = new appTimer(this);
        recordTime.startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recordTime.stopTimer("after");
    }
}


