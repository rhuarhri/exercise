package com.example.rhuarhri.androidexerciseapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public class checkPermissions {

    public String findMissingPermission(Context context)
    {
        if(bodySensors(context) == false)
        {

            return "health sensor permission";
        }

        if (bluetoothAccess(context) == false)
        {
            return "bluetooth permission";
        }

        if (bluetoothAdim(context) == false)
        {
            return "bluetooth admin permission";
        }

        return "";
    }


    public boolean check(Context context)
    {
        if (bodySensors(context) == true && bluetoothAccess(context) == true && bluetoothAdim(context)== true )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean bodySensors(Context context)
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BODY_SENSORS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        else
        {
            return true;
        }

    }

    private boolean bluetoothAccess(Context context)
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        else
        {
            return true;
        }

    }

    private boolean bluetoothAdim(Context context)
    {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return false;
        }
        else
        {
            return true;
        }
    }


}
