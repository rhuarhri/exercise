package com.example.rhuarhri.exerciseapp;
/*
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
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Set;

public class WatchActivity extends WearableActivity implements SensorEventListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, MessageApi.MessageListener {

    /*connecting to a smartwatch other bluetooth
    Type the follwing into the command prompt
    adb forward tcp:4444 localabstract:/adb-hub
    adb connect localhost:4444
     *

    private TextView mTextView;

    TextView heartRateTXT;
    //TextView performanceLevelTXT;
    Button pauseBTN;

    SensorManager healthSensorManager;
    Sensor heartRateSensor;
    Sensor accelerometer;

    private float currentHeartRate;
    private float currentMovement;

    private static final String COUNT_KEY = "key";
    private DataClient mDataClient;
    private int performanceLevel = 0;

    Set<Node> nodes;
    ConnectionUtils.NodeManager nodeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch);

        /*sensor setup*
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
                if (nodeManager.hasConnection() == true)
                {
                    Toast.makeText(WatchActivity.this, "Has connection", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(WatchActivity.this, "No connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApiIfAvailable(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();



                nodeManager = new ConnectionUtils.NodeManager(mGoogleApiClient, getString(R.string.CAPABILITY_MOBILE), "");

                /*
        nodeManager.setNodeListener(new ConnectionUtils.NodeListener() {
            @Override
            public void onNodeFound() {

            }*/






        /*
        Task<CapabilityInfo> capabilityInfoTask = Wearable.getCapabilityClient(this)
                .getCapability(getString(R.string.CAPABILITY_MOBILE)/*"@wear/android_wear_capabilities"*, CapabilityClient.FILTER_REACHABLE);

        capabilityInfoTask.addOnSuccessListener(new OnSuccessListener<CapabilityInfo>() {
            @Override
            public void onSuccess(CapabilityInfo capabilityInfo) {
                //CapabilityInfo capabilityInfo = task.getResult();
                nodes = capabilityInfo.getNodes();

                if (nodes.isEmpty() == true)
                {
                    Toast.makeText(WatchActivity.this, "no nodes available", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        /*
        capabilityInfoTask.addOnCompleteListener(new OnCompleteListener<CapabilityInfo>() {
            @Override
            public void onComplete(@NonNull Task<CapabilityInfo> task) {
                if (task.isSuccessful()) {
                    CapabilityInfo capabilityInfo = task.getResult();
                    nodes = capabilityInfo.getNodes();
                } else {
                    Toast.makeText(WatchActivity.this, "No node found", Toast.LENGTH_SHORT).show();
                }

                if (nodes.isEmpty() == true)
                {
                    Toast.makeText(WatchActivity.this, "no nodes available", Toast.LENGTH_SHORT).show();
                }
                else {

                    BigInteger bigInt = BigInteger.valueOf(66);

                    String bestNodeId = pickBestNodeId(nodes);
                    Wearable.getMessageClient(getApplicationContext()).sendMessage(
                            bestNodeId, "test", bigInt.toByteArray());
                }
            }
        });*

        Wearable.getMessageClient(this).addListener(new MessageClient.OnMessageReceivedListener() {
            @Override
            public void onMessageReceived(@NonNull MessageEvent messageEvent) {

                int message = ByteBuffer.wrap(messageEvent.getData()).getInt();
                showMesage(message);
            }
        });

        int message = 66;

        BigInteger bigInt = BigInteger.valueOf(66);

        //List nodesList = new ArrayList(nodes);

        //nodes.iterator().next()

        /*
        if(nodes == null)
        {
            Toast.makeText(WatchActivity.this, "node set is null", Toast.LENGTH_SHORT).show();
        }
        else if (nodes.isEmpty() == true)
        {
            Toast.makeText(WatchActivity.this, "no nodes available", Toast.LENGTH_SHORT).show();
        }
        else
        {

            Toast.makeText(WatchActivity.this, "nodes found", Toast.LENGTH_SHORT).show();

            String bestNodeId = pickBestNodeId(nodes);
            Wearable.getMessageClient(getApplicationContext()).sendMessage(
                    bestNodeId, "test", bigInt.toByteArray());

        }*




    }


    private void showMesage(int message)
    {
        Toast.makeText(this, "" + message, Toast.LENGTH_LONG).show();
    }

    private String pickBestNodeId(Set<Node> nodes) {
        String bestNodeId = null;
        // Find a nearby node or pick one arbitrarily
        for (Node node : nodes) {
            if (node.isNearby()) {
                return node.getId();
            }
            bestNodeId = node.getId();
        }
        return bestNodeId;
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
            performanceLevel = 0;
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {

    }

    /*
    private void sendPerformance(int currentPerformance) {
        PutDataMapRequest putDataMapReq = PutDataMapRequest.create("/count");
        putDataMapReq.getDataMap().putInt(COUNT_KEY, currentPerformance);
        PutDataRequest putDataReq = putDataMapReq.asPutDataRequest();
        Task<DataItem> putDataTask = mDataClient.putDataItem(putDataReq);
    }*
}
*/