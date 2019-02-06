package com.example.rhuarhri.androidexerciseapp;

/*
import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class appTimer {

    private Date currentTime;
    private long startTime;
    private long finishTime;
    private long duration;
    private String activity;
    private testerDB result;

    public appTimer(Context context)
    {
        result = Room.databaseBuilder(context, testerDB.class, "test-results").allowMainThreadQueries().build();
    }

    public List<tester> displayResults()
    {
        return result.testerDBInterface().getAll();
    }

    public void newTester(tester placeHolderData )
    {
        result.testerDBInterface().insertNew(placeHolderData);
    }

    public void startTimer()
    {
        currentTime = Calendar.getInstance().getTime();

        startTime = currentTime.getTime();
    }

    public void stopTimer(String activityName)
    {
        currentTime = Calendar.getInstance().getTime();

        finishTime = currentTime.getTime();

        activity = activityName;

        duration = finishTime - startTime;

        saveTimer();
    }

    private void saveTimer()
    {
        if (activity == "setup")
        {
            result.testerDBInterface().insertSetUpTime(duration);
        }
        else if (activity == "home")
        {
            result.testerDBInterface().insertHomeTime(duration);
        }
        else if (activity == "choose")
        {
            result.testerDBInterface().insertChooseTime(duration);
        }
        else if (activity == "complete")
        {
            result.testerDBInterface().insertCompleteTime(duration);
        }
        else if(activity == "all")
        {
            result.testerDBInterface().insertAllStatsTime(duration);
        }
        else if (activity == "after")
        {
            result.testerDBInterface().insertAfterTime(duration);
        }
        else
        {

        }

    }
}
*/