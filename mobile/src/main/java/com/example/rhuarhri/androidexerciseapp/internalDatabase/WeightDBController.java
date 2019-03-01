package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

/*
uses work manager which is a way to run code on a thread
provided by android jet pack library
 */

public class WeightDBController extends Worker {

    private double startWeight;
    private double currentWeight;
    private weightDBAccess weightDB;

    private Result threadResult = Worker.Result.failure();


    public WeightDBController(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        weightDB = Room.databaseBuilder(getApplicationContext(), weightDBAccess.class, "weightData" ).build();

        double inputWeight = getInputData().getDouble("weight", 0);
        String function = getInputData().getString("function");

        if (inputWeight <= 0)
        {
            Data errorData = new Data.Builder().putString("error", "weight can not be 0").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else
        {
            if (function == "add")
            {
                addWeight(inputWeight);
            }
            else if (function == "get")
            {
                getStoredWeight();
            }
            else
            {
                Data errorData = new Data.Builder().putString("error", "this function does not exist").build();

                threadResult = Worker.Result.failure(errorData);
            }
        }

        return threadResult;
    }

    private void addWeight(double Weight)
    {
        List<storedUserWeight> existingData = weightDB.storedWeight().getAll();

        if (existingData.isEmpty() == true)
        {
            //there is no data in the data base so a start weight can be added
            storedUserWeight newData = new storedUserWeight();
            newData.setCurrentWeight(Weight);
            newData.setStartWeight(Weight);
        }
        else
        {
            weightDB.storedWeight().addCurrentWeight(Weight);
        }

        threadResult = Worker.Result.success();
    }

    private void getStoredWeight()
    {
        double startWeight = 0;
        double currentWeight = 0;

        List<storedUserWeight> storedData = weightDB.storedWeight().getAll();

        startWeight = storedData.get(0).getStartWeight();

        currentWeight = storedData.get(0).getCurrentWeight();

        Data returnData = new Data.Builder().putDouble("start", startWeight).putDouble("current", currentWeight).build();

        threadResult = Worker.Result.success(returnData);
    }



}
