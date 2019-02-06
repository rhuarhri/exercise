package com.example.rhuarhri.exerciseapp;
/*
import android.service.carrier.CarrierMessagingService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataClient;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageClient;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, MessageApi.MessageListener {

    private static final String COUNT_KEY = "key";
    private int count = 0;

    Set<Node> nodes;
    String bestNodeId = null;




    TextView testTXT;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testTXT = (TextView) findViewById(R.id.testTXT);

        send = (Button) findViewById(R.id.sendBTN);



        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApiIfAvailable(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {





                /*
                Task<CapabilityInfo> capabilityInfoTask = Wearable.getCapabilityClient(getApplicationContext())
                        .getCapability(getString(R.string.CAPABILITY_ANDROID_WEAR), CapabilityClient.FILTER_REACHABLE);

                capabilityInfoTask.addOnuccessListener(new OnSuccessListener<CapabilityInfo>() {
                    @Override
                    public void onSuccess(CapabilityInfo capabilityInfo) {
                        nodes = capabilityInfo.getNodes();
                    }
                });*



                int message = 66;

                BigInteger bigInt = BigInteger.valueOf(66);

                //List nodesList = new ArrayList(nodes);

                //nodes.iterator().next()

                if(nodes == null)
                {
                    Toast.makeText(MainActivity.this, "node set is null", Toast.LENGTH_SHORT).show();
                }
                else if (nodes.isEmpty() == true)
                {
                    Toast.makeText(MainActivity.this, "no nodes available", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    Toast.makeText(MainActivity.this, "nodes found", Toast.LENGTH_SHORT).show();

                    bestNodeId = pickBestNodeId(nodes);
                    Wearable.getMessageClient(getApplicationContext()).sendMessage(
                            bestNodeId, "test", bigInt.toByteArray());

                }

                Wearable.getMessageClient(MainActivity.this).addListener(new MessageClient.OnMessageReceivedListener() {
                    @Override
                    public void onMessageReceived(@NonNull MessageEvent messageEvent) {

                        int message = ByteBuffer.wrap(messageEvent.getData()).getInt();
                        Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                    }
                });


            }


        });


    }

    /*
    private void setupVoiceTranscription() {

        // This example uses a Java 8 Lambda. Named or anonymous classes can also be used.
        CapabilityClient.OnCapabilityChangedListener capabilityListener =
                capabilityInfo -> { updateTranscriptionCapability(capabilityInfo); };
        Wearable.getCapabilityClient(context).addListener(
                capabilityListener,
                VOICE_TRANSCRIPTION_CAPABILITY_NAME);
    }*

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
    public void onConnected(@Nullable Bundle bundle) {
        Toast.makeText(this, "GoogleApiClient connected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        int message = ByteBuffer.wrap(messageEvent.getData()).getInt();
        Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_SHORT).show();
    }
}

/*Task<CapabilityInfo> capabilityInfoTask = Wearable.getCapabilityClient(getApplicationContext())
                        .getCapability("@wear/android_wear_capabilities", CapabilityClient.FILTER_REACHABLE);

                capabilityInfoTask.addOnCompleteListener(new OnCompleteListener<CapabilityInfo>() {
                    @Override
                    public void onComplete(@NonNull Task<CapabilityInfo> task) {
                        if (task.isSuccessful()) {
                            CapabilityInfo capabilityInfo = task.getResult();
                            nodes = capabilityInfo.getNodes();
                        } else {
                            Toast.makeText(MainActivity.this, "No node found", Toast.LENGTH_SHORT).show();
                        }
                    }

                });*

/*
    @Override
    public void onDataChanged(@NonNull DataEventBuffer dataEventBuffer) {
        for (DataEvent event : dataEventBuffer) {
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // DataItem changed
                DataItem item = event.getDataItem();
                if (item.getUri().getPath().compareTo("/count") == 0) {
                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
                    updateCount(dataMap.getInt(COUNT_KEY));
                }
            } else if (event.getType() == DataEvent.TYPE_DELETED) {
                // DataItem deleted
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Wearable.getDataClient(this).addListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Wearable.getDataClient(this).removeListener(this);
    }

    // Our method to update the count
    private void updateCount(int c) {
        testTXT.setText("performance at " + c);
    }*/