package com.example.rhuarhri.androidexerciseapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class StartExerciseActivity extends AppCompatActivity {

    int iteration = 0;
    TextView countdownTXT;
    ImageView startIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_exercise);

        countdownTXT = (TextView) findViewById(R.id.CountDownTXT);
        startIV = (ImageView) findViewById(R.id.StartIV);

        startIV.setVisibility(View.INVISIBLE);

        CountDownTimer timer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {

                iteration++;

                if (iteration == 1)
                {
                    countdownTXT.setText("3");
                }
                else if (iteration == 2)
                {
                    countdownTXT.setText("2");
                }
                else if (iteration == 3)
                {
                    countdownTXT.setText("1");
                }
                else if (iteration > 3)
                {
                    countdownTXT.setText("");
                    startIV.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFinish() {
                Intent exerciseScreen = new Intent(getApplicationContext(), ExerciseActivity.class);

                startActivity(exerciseScreen);
            }
        }.start();
    }
}
