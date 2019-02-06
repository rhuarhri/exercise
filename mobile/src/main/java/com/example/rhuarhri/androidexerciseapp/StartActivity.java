package com.example.rhuarhri.androidexerciseapp;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    Button googlePlayBTN;
    Button websiteBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        googlePlayBTN = (Button) findViewById(R.id.GooglePlayBTN);
        websiteBTN = (Button) findViewById(R.id.webBTN);

        googlePlayBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You would have gone to google play.", Toast.LENGTH_LONG).show();
            }
        });

        websiteBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "You would have gone to the company website.", Toast.LENGTH_LONG).show();
            }
        });

        CountDownTimer timeUntilStart = new CountDownTimer(5000, 5000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                goToSetupScreen();
            }
        }.start();
    }


    private void goToSetupScreen()
    {
        Intent setupScreen = new Intent(getApplicationContext(), SetupActivity.class);

        startActivity(setupScreen);
    }
}

