package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PerformanceDBController extends Worker {

    performanceDBLogic DBLogic;

    private Result threadResult = Worker.Result.failure();

    public PerformanceDBController(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        DBLogic = new performanceDBLogic(getApplicationContext());

        DBLogic.checkDataInDatabase();

        String function = getInputData().getString("function");

        if (function.equals("leg"))
        {
            DBLogic.increasePerformance("leg");
            getResult();
        }
        else if (function.equals("arm"))
        {
            DBLogic.increasePerformance("arm");
            getResult();
        }
        else if (function.equals("chest"))
        {
            DBLogic.increasePerformance("chest");
            getResult();
        }
        else if (function.equals("get"))
        {
            Data performanceData = DBLogic.getAllPerformance();

            if (DBLogic.Successful()) {
                threadResult = Worker.Result.success(performanceData);
            }
            else
            {
                Data errorData = new Data.Builder().putString("error", DBLogic.getError()).build();

                threadResult = Worker.Result.failure(errorData);
            }
        }
        else
        {
            Data errorData = new Data.Builder().putString("error", "this function does not exist").build();

            threadResult = Worker.Result.failure(errorData);
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

