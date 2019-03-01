package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class chosenExerciseDBController extends Worker {

    private chosenExerciseDBAccess ExerciseDB;

    private List<chosenExercises> allExercises;
    private String exerciseType = "";
    private String ImageName = "";
    private int minPerformance = 0;
    private int maxPerformance = 0;
    private int amount = 0;

    private Result threadResult = Worker.Result.failure();

    public chosenExerciseDBController(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        ExerciseDB = Room.databaseBuilder(getApplicationContext(), chosenExerciseDBAccess.class, "ExerciseData").build();

        String function = getInputData().getString("function");

        if (function == "add")
        {
            exerciseType = getInputData().getString("type");
            ImageName = getInputData().getString("image");
            minPerformance = getInputData().getInt("min", 0);
            maxPerformance = getInputData().getInt("max", 0);
            amount = getInputData().getInt("amount", 0);

            addExercise();
        }
        else if (function == "start")
        {
            getAllExercises();
        }
        else if (function == "end")
        {
            removeAllExercises();
        }
        else
        {
            Data errorData = new Data.Builder().putString("error", "this function does not exist").build();

            threadResult = Worker.Result.failure(errorData);
        }


        return threadResult;
    }

    private void addExercise()
    {
        if (exerciseType != "leg" || exerciseType != "arm" || exerciseType != "chest")
        {
            Data errorData = new Data.Builder().putString("error", "Exercise type must be either leg, arm or chest").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if(ImageName == "")
        {
            Data errorData = new Data.Builder().putString("error", "Each exercise must have an image").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (minPerformance <= 0)
        {
            Data errorData = new Data.Builder().putString("error", "Each exercise must have a minimum performance level between 1 and 9").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (maxPerformance <= 0)
        {
            Data errorData = new Data.Builder().putString("error", "Each exercise must have a maximum performance level between 1 and 9").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else if (amount <= 0)
        {
            Data errorData = new Data.Builder().putString("error", "Exercise amount must be greater than 0").build();

            threadResult = Worker.Result.failure(errorData);
        }
        else
        {
            chosenExercises newExercise = new chosenExercises();
            newExercise.setExerciseType(exerciseType);
            newExercise.setImageName(ImageName);
            newExercise.setMinimumPerformanceLevel(minPerformance);
            newExercise.setMaximumPerformanceLevel(maxPerformance);
            newExercise.setAmount(amount);

            ExerciseDB.storedExercises().addExercise(newExercise);

            threadResult = Worker.Result.success();
        }
    }

    private void removeAllExercises()
    {
        ExerciseDB.storedExercises().deleteAll();

        threadResult = Worker.Result.success();
    }

    private void getAllExercises()
    {
        allExercises = ExerciseDB.storedExercises().getAll();

        //TODO this could cause a problem
        @SuppressLint("RestrictedApi") Data allData = new Data.Builder().put("all", allExercises).build();

        threadResult = Worker.Result.success(allData);
    }
}
