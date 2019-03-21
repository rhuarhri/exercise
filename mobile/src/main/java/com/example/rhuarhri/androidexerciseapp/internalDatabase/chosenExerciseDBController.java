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
    //private String exerciseName = "";
    //private String exerciseType = "";
    //private String ImageName = "";
    //private int minPerformance = 0;
    //private int maxPerformance = 0;
    //private int amount = 0;
    //private long time = 0;

    chosenExerciseDBLogic DBLogic;

    private Result threadResult = Worker.Result.failure();


    public chosenExerciseDBController(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        //ExerciseDB = Room.databaseBuilder(getApplicationContext(), chosenExerciseDBAccess.class, "ExerciseData").build();

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

    /*
    public void addExercise()
    {
        if (exerciseType.equals("") /*|| !exerciseType.equals("arm") || !exerciseType.equals("chest")*)
        {
            Log.d("ERROR", "Exercise type must be either leg, arm or chest");

            Data errorData = new Data.Builder().putString("error", "Exercise type must be either leg, arm or chest").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (exerciseName.equals(""))
        {

            Log.d("ERROR", "Each exercise must have a name");

            Data errorData = new Data.Builder().putString("error", "Each exercise must have a name").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if(ImageName.equals(""))
        {

            Log.d("ERROR", "Each exercise must have an image");

            Data errorData = new Data.Builder().putString("error", "Each exercise must have an image").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (minPerformance <= 0)
        {

            Log.d("ERROR", "Each exercise must have a minimum performance level between 1 and 9");

            Data errorData = new Data.Builder().putString("error", "Each exercise must have a minimum performance level between 1 and 9").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (maxPerformance <= 0)
        {
            Log.d("ERROR", "Each exercise must have a maximum performance level between 1 and 9");

            Data errorData = new Data.Builder().putString("error", "Each exercise must have a maximum performance level between 1 and 9").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (amount <= 0)
        {
            Log.d("ERROR", "Exercise amount must be greater than 0");

            Data errorData = new Data.Builder().putString("error", "Exercise amount must be greater than 0").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (time <= 0)
        {
            Log.d("ERROR", "Exercise time must be greater than 0");

            Data errorData = new Data.Builder().putString("error", "Exercise time must be greater than 0").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else
        {
            chosenExercises newExercise = new chosenExercises();
            newExercise.setExerciseName(exerciseName);
            newExercise.setExerciseType(exerciseType);
            newExercise.setImageName(ImageName);
            newExercise.setMinimumPerformanceLevel(minPerformance);
            newExercise.setMaximumPerformanceLevel(maxPerformance);
            newExercise.setAmount(amount);
            newExercise.setTime(time);

            ExerciseDB.storedExercises().addExercise(newExercise);

            threadResult = Worker.Result.success();
        }
    }

    /*
    private void removeAllExercises()
    {
        ExerciseDB.storedExercises().deleteAll();

        threadResult = Worker.Result.success();
    }*

    private void removeOneExercise()
    {
        ExerciseDB.storedExercises().deteleOneExercise(exerciseName);

        threadResult = Worker.Result.success();
    }*/

    private void getAllExercises()
    {
        allExercises = ExerciseDB.storedExercises().getAll();

        //TODO this could cause a problem
        @SuppressLint("RestrictedApi") Data allData = new Data.Builder().put("all", allExercises).build();

        threadResult = Worker.Result.success(allData);
    }
}
