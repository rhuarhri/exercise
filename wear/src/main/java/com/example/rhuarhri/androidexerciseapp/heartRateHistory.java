package com.example.rhuarhri.androidexerciseapp;

import java.util.ArrayList;
import java.util.List;

public class heartRateHistory {

    List<Float> heartRate = new ArrayList<Float>();

    public void addHeartRate(float HeartRate)
    {
        heartRate.add(HeartRate);
        //prevents the list from becoming too big
        while (heartRate.size() > 100)
        {
            heartRate.remove(0);
        }
    }

    public long getAverage()
    {
        double total = 0;
        for (int i = 0; i < heartRate.size(); i++)
        {
            total += heartRate.get(i);
        }
        long average = Math.round(total / heartRate.size());
        return average;
    }
}
