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

    private double startWeight = 0;
    private double currentWeight = 0;
    private weightDBAccess weightDB;

    /*
    private Context appContext;

    public WeightDBController(Context context)
    {
        appContext = context;
    }*/


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
            if (function.equals("add"))
            {
                addWeight(inputWeight);
            }
            else if (function.equals("get"))
            {
                getStoredWeight();

                Data returnData = new Data.Builder().putDouble("start", startWeight).putDouble("current", currentWeight).build();

                threadResult = Worker.Result.success(returnData);
            }
            else
            {
                Data errorData = new Data.Builder().putString("error", "this function does not exist").build();

                threadResult = Worker.Result.failure(errorData);
            }
        }

        return threadResult;
    }

/*
    public String runOnMainThread(String Function, double weight)
    {
        weightDB = Room.databaseBuilder(appContext, weightDBAccess.class, "weightData" ).allowMainThreadQueries().build();

        double inputWeight = weight;
        String function = Function;

        if (inputWeight <= 0)
        {

            return "weight can not be 0";

        }
        else
        {
            if (function.equals("add"))
            {
                addWeight(inputWeight);
            }
            else if (function.equals("get"))
            {
                getStoredWeight();
            }
            else
            {

                return "this function does not exist";


            }
        }

        return "successful";
    }*/

    private void addWeight(double Weight)
    {
        List<storedUserWeight> existingData = weightDB.storedWeight().getAll();

        if (existingData.isEmpty() == true)
        {
            //there is no data in the data base so a start weight can be added
            storedUserWeight newData = new storedUserWeight();
            newData.setCurrentWeight(Weight);
            newData.setStartWeight(Weight);

            weightDB.storedWeight().add(newData);
        }
        else
        {
            weightDB.storedWeight().addCurrentWeight(Weight);
        }

        threadResult = Worker.Result.success();
    }

    private void getStoredWeight()
    {


        List<storedUserWeight> storedData = weightDB.storedWeight().getAll();

        startWeight = storedData.get(0).getStartWeight();

        currentWeight = storedData.get(0).getCurrentWeight();


    }

    public double getStartWeight() {
        return startWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }
}
