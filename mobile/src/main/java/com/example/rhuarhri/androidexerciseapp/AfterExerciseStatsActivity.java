package com.example.rhuarhri.androidexerciseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rhuarhri.androidexerciseapp.appToWatch.MessageHandler;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.PerformanceDBController;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class AfterExerciseStatsActivity extends AppCompatActivity {

    Button statsBTN;
    Button homeBTN;

    TextView caloriesTXT;
    TextView averageHeartRateTXT;

    OneTimeWorkRequest getPerformance;

    protected Handler myHandler;



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

        caloriesTXT = (TextView) findViewById(R.id.caloriesBurnedTXT);
        averageHeartRateTXT = (TextView) findViewById(R.id.avgHeartRateTXT);

        //get overall performance score
        Data threadData = new Data.Builder().putString("function", "get")
                .build();


        getPerformance = new OneTimeWorkRequest.Builder(PerformanceDBController.class)
                .setInputData(threadData).build();


        WorkManager.getInstance().enqueue(getPerformance);


        threadChecker checker = new threadChecker();

        checker.start();

        //get average heart rate

        myHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        AfterExerciseStatsActivity.Receiver messageReceiver = new AfterExerciseStatsActivity.Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        new MessageHandler(getApplicationContext(), AfterExerciseStatsActivity.this,
                "" + "heart").start();



    }


    //there is a way of doing this in older versions of work manger however unsure for new versions
    public class threadChecker extends Thread {

        public void run() {

            boolean weightThreadDone = false;

            while (weightThreadDone == false)
            {
                try{

                    weightThreadDone = WorkManager.getInstance().getWorkInfoById(getPerformance.getId()).get().getState().isFinished();
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
            error = WorkManager.getInstance().getWorkInfoById(getPerformance.getId()).get().getOutputData().getString("error");

            if (error == null/* == "" || error.isEmpty()*/) {


                    int legPerformance = WorkManager.getInstance().getWorkInfoById(getPerformance.getId()).get().getOutputData().getInt("leg", 9);

                    int armPerformance = WorkManager.getInstance().getWorkInfoById(getPerformance.getId()).get().getOutputData().getInt("arm", 9);

                    int chestPerformance = WorkManager.getInstance().getWorkInfoById(getPerformance.getId()).get().getOutputData().getInt("chest", 9);

                    displayPerformance(legPerformance, armPerformance, chestPerformance);

            }
            else
            {
                Log.d("ERROR: ",  error);
            }


        } catch (Exception e) {
            Log.d("ERROR MESSAGE: ", e.getMessage());
        }
    }

    private void displayPerformance(int leg, int arm, int chest)
    {
        caloriesTXT.setText("L: " + leg + " A: " + arm + " C: " + chest);
    }

    public void messageText(String newinfo) {
        if (!newinfo.isEmpty() || newinfo != "") {
            averageHeartRateTXT.setText(newinfo);

        }
    }

    //Define a nested class that extends BroadcastReceiver//

    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

            try{

                averageHeartRateTXT.setText(intent.getStringExtra("message"));


            }
            catch(Exception e)
            {

            }
        }
    }



}


