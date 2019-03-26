package com.example.rhuarhri.androidexerciseapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.wearable.MessageEvent;

import java.util.concurrent.TimeUnit;

import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;


public class WatchActivity extends WearableActivity implements SensorEventListener{

    /*connecting to a smartwatch other bluetooth
    Type the follwing into the command prompt
    adb forward tcp:4444 localabstract:/adb-hub
    adb connect localhost:4444
     */

    protected Handler messageHandler;


    boolean paused = false;
    periodicUpdater updater;

    TextView heartRateTXT;
    //TextView performanceLevelTXT;
    Button pauseBTN;

    SensorManager healthSensorManager;
    Sensor heartRateSensor;
    Sensor accelerometer;
    SensorEvent sensorData;
    boolean UpdateSensorData = true;

    private heartRateHistory HeartRateOverTime = new heartRateHistory();
    private float currentHeartRate;
    private float currentMovement;
    private boolean stopSending = false;

    //public for testing
    public int currentPerformanceLevel;
    private boolean okToSend;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        /*sensor setup*/
        healthSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        heartRateSensor = healthSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        accelerometer = healthSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        healthSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        healthSensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);


        // Enables Always-on
        setAmbientEnabled();

        //Message handler
        messageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Bundle stuff = msg.getData();
                messageText(stuff.getString("messageText"));
                return true;
            }
        });

        //Register to receive local broadcasts

        IntentFilter messageFilter = new IntentFilter(Intent.ACTION_SEND);
        WatchActivity.Receiver messageReceiver = new Receiver();
        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, messageFilter);

        heartRateTXT = (TextView) findViewById(R.id.heartRateTXT);

        //performanceLevelTXT = (TextView) findViewById(R.id.performanceTXT);

        pauseBTN = (Button) findViewById(R.id.pauseBTN);

        pauseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (paused == false)
                {
                    paused = true;
                    UpdateSensorData = false;
                    pauseBTN.setBackgroundResource(R.drawable.play);
                    if(stopSending = true) {
                        new MessageHandler(getApplicationContext(), WatchActivity.this, "0").start();
                    }
                    Log.d("Performance", "performance is 0");
                }
                else {
                    paused = false;
                    UpdateSensorData = true;
                    pauseBTN.setBackgroundResource(R.drawable.pause);
                    if(stopSending = true) {
                        new MessageHandler(getApplicationContext(), WatchActivity.this, "1").start();
                    }
                    Log.d("Performance", "performance is 1");

                }

            }
        });



    }

    public void messageText(String newinfo) {
        if (!newinfo.isEmpty() || newinfo != "") {
            if (newinfo.equals("heart"))
            {
                sendAverageHeartRate();
            }

        }
    }



    public class Receiver extends BroadcastReceiver {
        @Override

        public void onReceive(Context context, Intent intent) {

            try{

                String newinfo = intent.getStringExtra("message");

                if (newinfo.equals("heart"))
                {
                    sendAverageHeartRate();
                }


            }
            catch(Exception e)
            {

            }
        }
    }

    private void sendAverageHeartRate()
    {
        new MessageHandler(getApplicationContext(), WatchActivity.this,
                "" + HeartRateOverTime.getAverage()).start();
        stopSending = true;
        paused = true;
        UpdateSensorData = false;
        pauseBTN.setBackgroundResource(R.drawable.play);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {


        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            currentMovement = sensorEvent.values[0];

        }
        else
        {
            currentHeartRate = sensorEvent.values[0];
            heartRateTXT.setText("" + currentHeartRate);
            //HeartRateOverTime.addHeartRate(currentHeartRate);
        }

        //sensorData = sensorEvent;

        if (UpdateSensorData == true) {
            updater = new periodicUpdater();
            updater.start();


            if (paused == false) {

                new MessageHandler(getApplicationContext(), WatchActivity.this,
                        "" + calculatePerformance(currentHeartRate, currentMovement)).start();
                Log.d("Performance", "performance is " + calculatePerformance(currentHeartRate, currentMovement));


            } else {

                if (stopSending == false) {
                    new MessageHandler(getApplicationContext(), WatchActivity.this, "0").start();
                    Log.d("Performance", "performance is 0");
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public int calculatePerformance(float heartRate, float movement)
    {


        if (isMoving(movement) == true) {

            if(heartRate < 60)
            {
                return 0;



            }
            else if (heartRate > 60 && heartRate < 80)
            {
                return 1;


            }
            else if (heartRate > 80 && heartRate < 100)
            {
                return 2;


            }
            else if (heartRate > 100 && heartRate < 120)
            {
                return 3;


            }
            else if (heartRate > 120 && heartRate < 140)
            {
                return 4;


            }
            else if (heartRate > 140 && heartRate < 160)
            {
                return 5;


            }
            else if (heartRate > 160 && heartRate < 180)
            {
                return 6;


            }
            else if (heartRate > 180 && heartRate < 200)
            {
                return 7;


            }
            else if (heartRate > 200 && heartRate < 220)
            {
                return 8;


            }
            else if (heartRate > 220)
            {
                return 9;


            }

        }
        else
        {
            return 0;


        }

        return 0;

    }

    private boolean isMoving(float movement)
    {
        int movementRounded = Math.round(movement);

        if (movementRounded > 0 || movementRounded < 0)
        {
            return true;
        }
        else{
            return false;
        }
    }

    //This exists as the data from the smart watch's sensors changed too quickly
    //this caused the problem of the app being also constantly paused
    // as the performance level would instantly change from a positive number
    // to 0
    private class periodicUpdater extends Thread
    {


        public periodicUpdater() {

            UpdateSensorData = false;


        }

        public void run()
        {

                try {
                    Thread.sleep(500);

                    /*
                    if (sensorData.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                    {
                        currentMovement = sensorData.values[0];

                    }
                    else
                    {
                        currentHeartRate = sensorData.values[0];
                        heartRateTXT.setText("" + currentHeartRate);
                        HeartRateOverTime.addHeartRate(currentHeartRate);
                    }*/

                    HeartRateOverTime.addHeartRate(currentHeartRate);



                    UpdateSensorData = true;

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }

    }


}
