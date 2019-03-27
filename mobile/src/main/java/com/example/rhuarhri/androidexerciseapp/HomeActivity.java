package com.example.rhuarhri.androidexerciseapp;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rhuarhri.androidexerciseapp.internalDatabase.WeightDBController;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBController;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.storedUserWeight;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.weightDBAccess;

import java.util.List;
import java.util.concurrent.Executor;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class HomeActivity extends AppCompatActivity {

    Button saveBTN;
    Button statsBTN;
    Button playBTN;
    EditText WeightET;

    OneTimeWorkRequest addWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        saveBTN = (Button) findViewById(R.id.saveBTN);

        WeightET = (EditText) findViewById(R.id.weightET);

        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double addingWeight = Double.parseDouble(WeightET.getText().toString());

                Data threadData = new Data.Builder().putString("function", "add").putDouble("weight", addingWeight)
                        .build();

                addWeight = new OneTimeWorkRequest.Builder(WeightDBController.class)
                        .setInputData(threadData).build();

                WorkManager.getInstance().enqueue(addWeight);

                Toast.makeText(HomeActivity.this, "Weight saved", Toast.LENGTH_SHORT).show();

                WorkManager.getInstance().getWorkInfoById(addWeight.getId()).addListener(() ->
                        runOnUiThread(HomeActivity.this::test), new CurrentThreadExecutor());
            }
        });

        statsBTN = (Button) findViewById(R.id.statsBTN);

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

    public void test() {

        //after the thread has run this function should be called
        //caused problems during development
    }

    class CurrentThreadExecutor implements Executor {
        public void execute(@NonNull Runnable r) {
            r.run();
        }
    }
}

