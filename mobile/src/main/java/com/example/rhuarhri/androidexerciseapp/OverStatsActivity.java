package com.example.rhuarhri.androidexerciseapp;
import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rhuarhri.androidexerciseapp.internalDatabase.WeightDBController;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class OverStatsActivity extends AppCompatActivity implements LifecycleOwner {

    Button homeBTN;
    ProgressBar nowPB;
    ProgressBar wasPB;

    double startWeight = 0;
    double currentWeight = 0;

    OneTimeWorkRequest getCurrentWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_stats);
        homeBTN = (Button) findViewById(R.id.homeBTN);
        nowPB = (ProgressBar) findViewById(R.id.nowWeightPB);
        wasPB = (ProgressBar) findViewById(R.id.wasWeightPB);

        Data threadData = new Data.Builder().putString("function", "get").putDouble("weight", 1)
                .build();

        getCurrentWeight = new OneTimeWorkRequest.Builder(WeightDBController.class).setInputData(threadData).build();

        WorkManager.getInstance().enqueue(getCurrentWeight);

        threadChecker check = new threadChecker();
        check.start();

        homeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent homeScreen = new Intent(getApplicationContext(), HomeActivity.class);

                startActivity(homeScreen);


            }
        });


    }

    //there is a way of doing this in older versions of work manger however unsure for new versions
    public class threadChecker extends Thread {

        public void run() {

            boolean weightThreadDone = false;

            while (weightThreadDone == false)
            {
                try{

                    weightThreadDone = WorkManager.getInstance().getWorkInfoById(getCurrentWeight.getId()).get().getState().isFinished();
                    Thread.sleep(500);
                }
                catch (Exception e)
                {

                }
            }

            getThreadResult();

        }
        }



    public void getThreadResult()
    {

                try {

                    String error  = "";
                    error = WorkManager.getInstance().getWorkInfoById(getCurrentWeight.getId()).get().getOutputData().getString("error");

                    if (error == "" || error == null) {
                        startWeight =
                                WorkManager.getInstance().getWorkInfoById(getCurrentWeight.getId()).get().getOutputData().getDouble("start", 0);

                        currentWeight =
                                WorkManager.getInstance().getWorkInfoById(getCurrentWeight.getId()).get().getOutputData().getDouble("current", 0);

                        setUpProgressBars(startWeight, currentWeight);
                    }
                    else
                    {

                    }


                } catch (Exception e) {

                }
            }


    private void setUpProgressBars(double startWeight, double currentWeight)
    {
        double maxValue = 0;

        if (startWeight > currentWeight)
        {
            maxValue = startWeight;
        }
        else if (currentWeight > startWeight)
        {
            maxValue = currentWeight;
        }
        else{
            maxValue = startWeight;
        }

        nowPB.setMax((int) Math.round(maxValue));
        wasPB.setMax((int) Math.round(maxValue));

        nowPB.setProgress((int) Math.round(currentWeight));
        wasPB.setProgress((int) Math.round(startWeight));
    }

}

