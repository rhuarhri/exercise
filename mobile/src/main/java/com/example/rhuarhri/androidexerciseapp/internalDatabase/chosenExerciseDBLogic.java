package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;

public class chosenExerciseDBLogic {

    private chosenExerciseDBAccess ExerciseDB;

    private class result {
        public boolean isSuccessful;
        public String errorMessage;
    }

    private result returnResult = new result();

    public chosenExerciseDBLogic(Context context)
    {
        ExerciseDB = Room.databaseBuilder(context, chosenExerciseDBAccess.class, "ExerciseData").build();
    }

    //used in unit testing
    public chosenExerciseDBLogic(chosenExerciseDBAccess TestDB)
    {
        ExerciseDB = TestDB;
    }

    public String getError()
    {
        return returnResult.errorMessage;
    }

    public boolean Successful()
    {
        return returnResult.isSuccessful;
    }

    public void addExercise(String exerciseType, String exerciseName, String ImageName, int minPerformance,
                            int maxPerformance, int amount, long time)
    {
        if (exerciseType.equals("") /*|| !exerciseType.equals("arm") || !exerciseType.equals("chest")*/)
        {
            Log.d("ERROR", "Exercise type must be either leg, arm or chest");

            failure("Exercise type must be either leg, arm or chest");


        }
        else if (exerciseName.equals(""))
        {

            Log.d("ERROR", "Each exercise must have a name");

            failure("Each exercise must have a name");
        }
        else if(ImageName.equals(""))
        {

            Log.d("ERROR", "Each exercise must have an image");

            failure("Each exercise must have an image");


        }
        else if (minPerformance <= 0)
        {

            Log.d("ERROR", "Each exercise must have a minimum performance level between 1 and 9");

            failure("Each exercise must have a minimum performance level between 1 and 9");


        }
        else if (maxPerformance <= 0)
        {
            Log.d("ERROR", "Each exercise must have a maximum performance level between 1 and 9");

            failure("Each exercise must have a maximum performance level between 1 and 9");


        }
        else if (amount <= 0)
        {
            Log.d("ERROR", "Exercise amount must be greater than 0");

            failure( "Exercise amount must be greater than 0");


        }
        else if (time <= 0)
        {
            Log.d("ERROR", "Exercise time must be greater than 0");

            failure("Exercise time must be greater than 0");


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

            success();
        }
    }

    public void removeAllExercises()
    {
        ExerciseDB.storedExercises().deleteAll();
        success();
    }

    public void removeOneExercise(String exerciseName)
    {
        ExerciseDB.storedExercises().deteleOneExercise(exerciseName);
        success();
    }

    private void success()
    {
        result ReturnResult = new result();
        returnResult.errorMessage = "";
        returnResult.isSuccessful = true;

        returnResult = ReturnResult;
    }

    private void failure(String error)
    {
        result ReturnResult = new result();
        returnResult.errorMessage = error;
        returnResult.isSuccessful = false;

        returnResult = ReturnResult;
    }
}
