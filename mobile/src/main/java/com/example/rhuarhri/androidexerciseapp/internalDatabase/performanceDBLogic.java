package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;

public class performanceDBLogic {

    private performanceDBAccess performanceDB;
    List<storedUserPerformance> allPerformanceData;

    private class result {
        public boolean isSuccessful;
        public String errorMessage;
    }

    private result returnResult = new result();

    public performanceDBLogic(Context context)
    {
        performanceDB = Room.databaseBuilder(context, performanceDBAccess.class, "performanceData").build();
    }

    //for testing
    public performanceDBLogic(performanceDBAccess TestDB)
    {
        performanceDB = TestDB;
    }

    public String getError()
    {
        return returnResult.errorMessage;
    }

    public boolean Successful()
    {
        return returnResult.isSuccessful;
    }

    public void checkDataInDatabase()
    {
        allPerformanceData = performanceDB.storedPerformance().getAll();

        if (performanceDB.storedPerformance().databaseSize() <= 0)
        {
            //no data in database
            storedUserPerformance newPerformance = new storedUserPerformance();

            //finds the current date
            newPerformance.setDurationBaseFromCalender(Calendar.getInstance().getTime().getTime());

            newPerformance.setTimesExercisingArms(0);
            newPerformance.setTimesExercisingChest(0);
            newPerformance.setTimesExercisingLegs(0);

            performanceDB.storedPerformance().AddNewPerformance(newPerformance);

            success();
        }
        else
        {

        }
    }

    public Data getAllPerformance()
    {
        allPerformanceData = performanceDB.storedPerformance().getAll();

        Data performanceData;

        if (allPerformanceData.isEmpty() == true)
        {
            failure("data base empty");
            performanceData = new Data.Builder()
                    .putInt("leg", 0)
                    .putInt("arm", 0)
                    .putInt("chest", 0)
                    .build();
        }
        else {

            int legExercises = allPerformanceData.get(0).getTimesExercisingLegs();

            int armExercises = allPerformanceData.get(0).getTimesExercisingArms();

            int chestExercises = allPerformanceData.get(0).getTimesExercisingChest();

            long duration = allPerformanceData.get(0).getDurationBaseFromCalender();

            int legPerformance = calculatePerformance(legExercises);

            int armPerformance = calculatePerformance(armExercises);

            int chestPerformance = calculatePerformance(chestExercises);

            performanceData = new Data.Builder()
                    .putInt("leg", legPerformance)
                    .putInt("arm", armPerformance)
                    .putInt("chest", chestPerformance)
                    .build();

            success();
        }

        return performanceData;

    }

    public int calculatePerformance(int exerciseAmount)
    {
        /*
        performance scale
        1 is good
        2 is average
        3 is bad
        */

        long duration = getDuration(allPerformanceData.get(0).getDurationBaseFromCalender());

        /*it is expected that a person would have good performance
        if they do five exercises on one part of the body
         */
        long expected = 5 * duration;

        long actual = exerciseAmount;

        if (actual >= expected)
        {
            //good performance
            success();
            return 1;
        }
        else if (actual > (expected /2))
        {
            //if actual greater than 50% then performance is average
            success();
            return 2;
        }
        else if (actual < (expected / 2))
        {
            //if actual less than 50% then performance is poor
            success();
            return 3;
        }
        else
        {
            //error
            failure("unable to calculate performance");
            return 0;
        }


    }

    public long getDuration(long startTime)
    {
        Calendar getDate = Calendar.getInstance();
        long currentDate = getDate.getTime().getTime();


        long duration = currentDate - startTime;

        long durationIDays = duration / (24 * 60 * 60 * 1000);

        return durationIDays;
    }

    public void increasePerformance(String bodyPart)
    {

        if (bodyPart.equals("leg"))
        {
            int legExercises = performanceDB.storedPerformance().getLegPerformance().get(0);

            legExercises++;

            performanceDB.storedPerformance().increaseLegPerformance(legExercises);


            success();
        }
        else if (bodyPart.equals("arm"))
        {
            int armExercises = performanceDB.storedPerformance().getArmPerformance().get(0);

            armExercises++;

            performanceDB.storedPerformance().increaseArmPerformance(armExercises);


            success();

        }
        else if (bodyPart.equals("chest"))
        {
            int chestExercises = performanceDB.storedPerformance().getchestPerformance().get(0);

            chestExercises++;

            performanceDB.storedPerformance().increaseChestPerformance(chestExercises);


            success();
        }
        else
        {
            failure("do not understand input");
        }

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
