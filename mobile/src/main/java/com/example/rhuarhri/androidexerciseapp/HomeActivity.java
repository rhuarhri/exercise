package com.example.rhuarhri.androidexerciseapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button saveBTN;
    Button statsBTN;
    Button playBTN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        saveBTN = (Button) findViewById(R.id.saveBTN);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Weight saved", Toast.LENGTH_SHORT).show();
            }
        });

        statsBTN  = (Button) findViewById(R.id.statsBTN);

        statsBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent overallStatsScreen = new Intent(getApplicationContext(), OverStatsActivity.class);

                startActivity(overallStatsScreen);
            }
        });

        playBTN = (Button) findViewById(R.id.playBTN);

        playBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseExerciseScreen = new Intent(getApplicationContext(), ChooseExerciseActivity.class);

                startActivity(chooseExerciseScreen);
            }
        });


    }
}

