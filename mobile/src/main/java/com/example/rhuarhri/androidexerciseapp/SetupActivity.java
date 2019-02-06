package com.example.rhuarhri.androidexerciseapp;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SetupActivity extends AppCompatActivity {

    Button connectBTN;
    Spinner deviceDropDownList;
    setupError errorFrag;
    FragmentManager fragManager;

    List<String> devices;

    String chosenDevice;
    String requiredDevice;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        //Toast.makeText(this, "HINT: Choose device1", Toast.LENGTH_LONG).show();

        deviceDropDownList = (Spinner) findViewById(R.id.deviceSPR);
        setUpSpinner();
        /*
        deviceDropDownList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chosenDevice = deviceDropDownList.getSelectedItem().toString();
            }
        });*/



        fragManager = getFragmentManager();

        errorFrag = (setupError) fragManager.findFragmentById(R.id.errorFragment);

        fragManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .hide(errorFrag)
                .commit();

        connectBTN = (Button) findViewById(R.id.connectBTN);

        connectBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chosenDevice = deviceDropDownList.getSelectedItem().toString();
                checkInput();
            }
        });


    }

    private void checkInput()
    {
        if (chosenDevice == requiredDevice)
        {
            goToHomeScreen();
        }
        else{
            //Toast.makeText(this, "HINT: Choose device1", Toast.LENGTH_LONG).show();

            fragManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                    .show(errorFrag)
                    .commit();

        }
    }

    private void goToHomeScreen()
    {
        Intent HomeScreen = new Intent(getApplicationContext(), HomeActivity.class);

        startActivity(HomeScreen);
    }

    private void setUpSpinner()
    {
        devices = new ArrayList<String>();
        devices.add("device1");
        devices.add("device2");
        devices.add("device3");

        ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this, R.array.devices, android.R.layout.simple_spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        deviceDropDownList.setAdapter(dataAdapter);

        requiredDevice = deviceDropDownList.getSelectedItem().toString();

    }

/*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        chosenDevice = devices.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        chosenDevice = "";
    }*/

    
}


