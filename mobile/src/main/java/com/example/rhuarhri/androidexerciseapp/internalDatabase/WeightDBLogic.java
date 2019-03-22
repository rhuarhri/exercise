package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

import androidx.work.Data;
import androidx.work.Worker;

public class WeightDBLogic {

    private weightDBAccess weightDB;

    private class result {
        public boolean isSuccessful;
        public String errorMessage;
    }

    private result returnResult = new result();



    public WeightDBLogic(Context context)
    {
        weightDB = Room.databaseBuilder(context, weightDBAccess.class, "weightData" ).build();
    }

    //for testing
    public WeightDBLogic(weightDBAccess TestDB)
    {
        weightDB = TestDB;
    }

    public String getError()
    {
        return returnResult.errorMessage;
    }

    public boolean Successful()
    {
        return returnResult.isSuccessful;
    }


    public void addWeight(double Weight)
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

        success();
    }

    public Data getStoredWeight()
    {

        List<storedUserWeight> storedData = weightDB.storedWeight().getAll();

        double startWeight = storedData.get(0).getStartWeight();

        double currentWeight = storedData.get(0).getCurrentWeight();

        success();

        return new Data.Builder().putDouble("start", startWeight).putDouble("current", currentWeight).build();

    }

    /*public double getStartWeight() {
        return startWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }*/

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
