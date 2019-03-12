package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PerformanceDBController extends Worker {

    private performanceDBAccess performanceDB;

    List<storedUserPerformance> allPerformanceData;

    private int legPerformance = 0;
    private int legExercises = 0;
    private int armPerformance = 0;
    private int armExercises = 0;
    private int chestPerformance = 0;
    private int chestExercises = 0;

    private long duration = 0;

    private Result threadResult = Worker.Result.failure();

    public PerformanceDBController(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        performanceDB = Room.databaseBuilder(getApplicationContext(), performanceDBAccess.class, "performanceData").build();

        checkDataInDatabase();


        String function = getInputData().getString("function");

        if (function.equals("leg"))
        {
            increasePerformance("leg");
        }
        else if (function.equals("arm"))
        {
            increasePerformance("arm");
        }
        else if (function.equals("chest"))
        {
            increasePerformance("chest");
        }
        else if (function.equals("get"))
        {
            getAllPerformance();
        }
        else
        {
            Data errorData = new Data.Builder().putString("error", "this function does not exist").build();

            threadResult = Worker.Result.failure(errorData);
        }


        return threadResult;
    }

    private void checkDataInDatabase()
    {
        allPerformanceData = performanceDB.storedPerformance().getAll();

        if (allPerformanceData.isEmpty() == true)
        {
            //no data in database
            storedUserPerformance newPerformance = new storedUserPerformance();

            //finds the current date
            newPerformance.setDurationBaseFromCalender(Calendar.getInstance().getTime().getTime());

            newPerformance.setTimesExercisingArms(0);
            newPerformance.setTimesExercisingChest(0);
            newPerformance.setTimesExercisingLegs(0);

            performanceDB.storedPerformance().AddNewPerformance(newPerformance);
        }
        else
        {

        }
    }

    private void getAllPerformance()
    {

         allPerformanceData = performanceDB.storedPerformance().getAll();

        legExercises = allPerformanceData.get(0).getTimesExercisingLegs();

        armExercises = allPerformanceData.get(0).getTimesExercisingArms();

        chestExercises = allPerformanceData.get(0).getTimesExercisingChest();

        duration = allPerformanceData.get(0).getDurationBaseFromCalender();

        legPerformance = calculatePerformance(legExercises);

        armPerformance = calculatePerformance(armExercises);

        chestPerformance = calculatePerformance(chestExercises);

        Data performanceData = new Data.Builder()
                .putInt("leg", legPerformance)
                .putInt("arm", armPerformance)
                .putInt("chest", chestPerformance)
                .build();

        threadResult = Worker.Result.success(performanceData);

    }

    private int calculatePerformance(int exerciseAmount)
    {
        /*
        performance scale
        1 is good
        2 is average
        3 is bad
        */

        long duration = getDuration();

        /*it is expected that a person would have good performance
        if they do five exercises on one part of the body
         */
        long expected = 5 * duration;

        long actual = exerciseAmount;

        if (actual >= expected)
        {
            //good performance
            return 1;
        }
        else if (actual > (expected /2))
        {
            //if actual greater than 50% then performance is average
            return 2;
        }
        else if (actual < (expected / 2))
        {
            //if actual less than 50% then performance is poor
            return 1;
        }
        else
        {
            //error
            return 0;
        }


    }

    private long getDuration()
    {
        Calendar getDate = Calendar.getInstance();
        long currentDate = getDate.getTime().getTime();

        long startTime = duration;

        long duration = currentDate - startTime;

        long durationIDays = duration / (24 * 60 * 60 * 1000);

        return durationIDays;
    }

    private void increasePerformance(String bodyPart)
    {

        if (bodyPart == "leg")
        {
            legExercises = performanceDB.storedPerformance().getLegPerformance().get(0);

            legExercises++;

            performanceDB.storedPerformance().increaseLegPerformance(legPerformance);
        }
        else if (bodyPart == "arm")
        {
            armExercises = performanceDB.storedPerformance().getArmPerformance().get(0);

            armExercises++;

            performanceDB.storedPerformance().increaseArmPerformance(armExercises);

        }
        else if (bodyPart == "chest")
        {
            chestExercises = performanceDB.storedPerformance().getchestPerformance().get(0);

            chestExercises++;

            performanceDB.storedPerformance().increaseChestPerformance(chestExercises);
        }
        else
        {

        }
    }





    /*


    return list(int leg performance, int arm performance, int chest performance)

     */







}

