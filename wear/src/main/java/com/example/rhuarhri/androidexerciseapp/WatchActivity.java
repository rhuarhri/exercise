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


public class WatchActivity extends WearableActivity implements SensorEventListener{

    /*connecting to a smartwatch other bluetooth
    Type the follwing into the command prompt
    adb forward tcp:4444 localabstract:/adb-hub
    adb connect localhost:4444
     */

    private TextView mTextView;

    TextView heartRateTXT;
    //TextView performanceLevelTXT;
    Button pauseBTN;

    SensorManager healthSensorManager;
    Sensor heartRateSensor;
    Sensor accelerometer;

    private float currentHeartRate;
    private float currentMovement;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        /*sensor setup*/
        healthSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        heartRateSensor = healthSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);

        accelerometer = healthSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        healthSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        healthSensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_FASTEST);


        // Enables Always-on
        setAmbientEnabled();

        heartRateTXT = (TextView) findViewById(R.id.heartRateTXT);

        //performanceLevelTXT = (TextView) findViewById(R.id.performanceTXT);

        pauseBTN = (Button) findViewById(R.id.pauseBTN);

        pauseBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
            //heartRateTXT.setText("" + currentHeartRate);
        }

        calculatePerformance(currentHeartRate, currentMovement);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void calculatePerformance(float heartRate, float movement)
    {


        if (isMoving(movement) == true) {

            if(heartRate < 80)
            {
                //performanceLevelTXT.setText("0");
                //sendPerformance(0);
            }
            else if (heartRate > 80 && heartRate < 100)
            {
                //performanceLevelTXT.setText("1");
                //sendPerformance(1);
            }
            else if (heartRate > 100 && heartRate < 120)
            {
                //performanceLevelTXT.setText("2");
                //sendPerformance(2);
            }
            else if (heartRate > 120 && heartRate < 140)
            {
                //performanceLevelTXT.setText("3");
                //sendPerformance(3);
            }
            else if (heartRate > 140 && heartRate < 160)
            {
                //performanceLevelTXT.setText("4");
                //sendPerformance(4);
            }
            else if (heartRate > 160 && heartRate < 180)
            {
                //performanceLevelTXT.setText("5");
                //sendPerformance(5);
            }
            else if (heartRate > 180 && heartRate < 200)
            {
                //performanceLevelTXT.setText("6");
                //sendPerformance(6);
            }
            else if (heartRate > 200 && heartRate < 220)
            {
                //performanceLevelTXT.setText("7");
                //sendPerformance(7);
            }
            else if (heartRate > 220 && heartRate < 240)
            {
                //performanceLevelTXT.setText("8");
                //sendPerformance(8);
            }
            else if (heartRate > 240)
            {
                //performanceLevelTXT.setText("9");
                //sendPerformance(9);
            }

        }
        else
        {
            //performanceLevelTXT.setText("0");
            //performanceLevel = 0;
            //sendPerformance(0);
        }

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


    /*
    */
}
