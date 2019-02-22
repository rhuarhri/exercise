package com.example.rhuarhri.androidexerciseapp;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class periodicSender extends Worker {

    Context appContext;

    public periodicSender(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        appContext = context;
    }

    @NonNull
    @Override
    public Result doWork() {


        int performanceLevel = getInputData().getInt("performance", 0);



        //new MessageHandler(appContext,  , "4");
        return Worker.Result.success();
    }
}
