package com.example.rhuarhri.androidexerciseapp;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.activity.WearableActivity;
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

    boolean paused = false;

    TextView heartRateTXT;
    //TextView performanceLevelTXT;
    Button pauseBTN;

    SensorManager healthSensorManager;
    Sensor heartRateSensor;
    Sensor accelerometer;

    private float currentHeartRate;
    private float currentMovement;
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

        heartRateTXT = (TextView) findViewById(R.id.heartRateTXT);

        //performanceLevelTXT = (TextView) findViewById(R.id.performanceTXT);

        pauseBTN = (Button) findViewById(R.id.pauseBTN);

        pauseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (paused == false)
                {
                    paused = true;
                    pauseBTN.setBackgroundResource(R.drawable.play);
                    new MessageHandler(getApplicationContext(), WatchActivity.this, "0").start();
                }
                else {
                    paused = false;
                    pauseBTN.setBackgroundResource(R.drawable.pause);
                    new MessageHandler(getApplicationContext(), WatchActivity.this, "1").start();
                }

            }
        });

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
        }

        if (paused == false) {

            new MessageHandler(getApplicationContext(), WatchActivity.this,
                    "" + calculatePerformance(currentHeartRate, currentMovement)).start();

        }
        else{

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public int calculatePerformance(float heartRate, float movement)
    {


        if (isMoving(movement) == true) {

            if(heartRate < 80)
            {
                return 0;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "0").start();

            }
            else if (heartRate > 80 && heartRate < 100)
            {
                return 1;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "1").start();
            }
            else if (heartRate > 100 && heartRate < 120)
            {
                return 2;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "2").start();
            }
            else if (heartRate > 120 && heartRate < 140)
            {
                return 3;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "3").start();
            }
            else if (heartRate > 140 && heartRate < 160)
            {
                return 4;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "4").start();
            }
            else if (heartRate > 160 && heartRate < 180)
            {
                return 5;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "5").start();
            }
            else if (heartRate > 180 && heartRate < 200)
            {
                return 6;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "6").start();
            }
            else if (heartRate > 200 && heartRate < 220)
            {
                return 7;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "7").start();
            }
            else if (heartRate > 220 && heartRate < 240)
            {
                return 8;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "8").start();
            }
            else if (heartRate > 240)
            {
                return 9;

                //new MessageHandler(getApplicationContext(), WatchActivity.this, "9").start();
            }

        }
        else
        {
            return 0;

            //new MessageHandler(getApplicationContext(), WatchActivity.this, "0").start();
        }

        return 0;

    }

    private boolean isMoving(float movement)
    {
        int movementRounded = Math.round(movement);

        if (movementRounded > 2 || movementRounded < -2)
        {
            return true;
        }
        else{
            return false;
        }
    }


}
