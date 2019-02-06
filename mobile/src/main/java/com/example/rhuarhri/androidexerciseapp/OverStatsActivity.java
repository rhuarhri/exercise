package com.example.rhuarhri.androidexerciseapp;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OverStatsActivity extends AppCompatActivity {

    Button homeBTN;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_stats);
        homeBTN = (Button) findViewById(R.id.homeBTN);

        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);

                startActivity(homeScreen);
            }
        });


    }


}

