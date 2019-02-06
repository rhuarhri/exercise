package com.example.rhuarhri.androidexerciseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class completeActivity extends AppCompatActivity {

    Button exitBTN;
    Button homeBTN;
    Button statsBTN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete);
        exitBTN = (Button) findViewById(R.id.ExitBTN);

        exitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        statsBTN = (Button) findViewById(R.id.statsBTN);

        statsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StatsAfterScreen = new Intent(getApplicationContext(), AfterExerciseStatsActivity.class);

                startActivity(StatsAfterScreen);
            }
        });

    }
}
