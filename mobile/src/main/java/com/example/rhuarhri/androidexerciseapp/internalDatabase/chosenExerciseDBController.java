package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class chosenExerciseDBController extends Worker {

    private chosenExerciseDBAccess ExerciseDB;

    private List<chosenExercises> allExercises;

    chosenExerciseDBLogic DBLogic;

    private Result threadResult = Worker.Result.failure();

    public chosenExerciseDBController(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        DBLogic = new chosenExerciseDBLogic(getApplicationContext());

        String function = getInputData().getString("function");

        if (function.equals("add"))
        {
            String exerciseName = getInputData().getString("name");
            String exerciseType = getInputData().getString("type");
            String ImageName = getInputData().getString("image");
            int minPerformance = getInputData().getInt("min", 0);
            int maxPerformance = getInputData().getInt("max", 0);
            int amount = getInputData().getInt("amount", 0);
            long time = getInputData().getLong("time", 0);

            DBLogic.addExercise(exerciseType, exerciseName, ImageName, minPerformance, maxPerformance, amount, time);

            getResult();

        }
        else if (function.equals("start"))
        {
            getAllExercises();
        }
        else if (function.equals("end"))
        {
            DBLogic.removeAllExercises();
            getResult();
        }
        else if (function.equals("delete"))
        {
            String exerciseName = getInputData().getString("name");

            DBLogic.removeOneExercise(exerciseName);

            getResult();
        }
        else
        {
            Log.d("ERROR", "this function does not exist");
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

    private void getAllExercises()
    {
        allExercises = ExerciseDB.storedExercises().getAll();

        //TODO this could cause a problem
        @SuppressLint("RestrictedApi") Data allData = new Data.Builder().put("all", allExercises).build();

        threadResult = Worker.Result.success(allData);
    }
}
