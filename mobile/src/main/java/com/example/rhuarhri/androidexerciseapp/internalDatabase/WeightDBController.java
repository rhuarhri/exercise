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


    private WeightDBLogic DBLogic;

    private Result threadResult = Worker.Result.failure();

    public WeightDBController(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        DBLogic = new WeightDBLogic(getApplicationContext());

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
                DBLogic.addWeight(inputWeight);

                getResult();
            }
            else if (function.equals("get"))
            {
                Data returnData = DBLogic.getStoredWeight();

                if (DBLogic.Successful() == true)
                {
                    threadResult = Worker.Result.success(returnData);
                }
                else
                {
                    threadResult = Worker.Result.failure();
                }

            }
            else
            {
                Data errorData = new Data.Builder().putString("error", "this function does not exist").build();

                threadResult = Worker.Result.failure(errorData);
            }
        }

        return threadResult;
    }

    private void getResult()
    {
        if (DBLogic.Successful()) {
            threadResult = Worker.Result.success();
        }
        else
        {
            Data errorData = new Data.Builder().putString("error", DBLogic.getError()).build();

            threadResult = Worker.Result.failure(errorData);
        }
    }
}
