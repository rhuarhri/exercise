package com.example.rhuarhri.androidexerciseapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.rhuarhri.androidexerciseapp.internalDatabase.WeightDBLogic;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBAccess;

import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBLogic;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.performanceDBAccess;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.performanceDBLogic;
import com.example.rhuarhri.androidexerciseapp.internalDatabase.weightDBAccess;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Calendar;

import androidx.work.Data;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    //auto generated by android studio kept as reference
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.rhuarhri.androidexerciseapp", appContext.getPackageName());
    }
    //below are tests specific to the project

    private chosenExerciseDBAccess ExerciseDB;

    @Before
    public void createChosenExerciseDb() {
        Context context = InstrumentationRegistry.getTargetContext();// ApplicationProvider.getApplicationContext();
        ExerciseDB = Room.inMemoryDatabaseBuilder(context, chosenExerciseDBAccess.class).build();
    }

    @After
    public void closeChosenExerciseDb() throws IOException {
        ExerciseDB.close();
    }

    @Test
    public void chosenExerciseFinishExercises()throws Exception
    {

        chosenExerciseDBLogic test = new chosenExerciseDBLogic(ExerciseDB);

        test.addExercise("arm", "test", "test", 4,6, 2, 60000);

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);

        test.removeAllExercises();

        result = test.Successful();

        assertEquals(expected, result);

    }



    @Test
    public void chosenExerciseRemoveExercise()throws Exception
    {

        chosenExerciseDBLogic test = new chosenExerciseDBLogic(ExerciseDB);

        test.addExercise("arm", "test", "test", 4,6, 2, 60000);

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);

        test.removeOneExercise("test");

        result = test.Successful();

        assertEquals(expected, result);

    }

    private performanceDBAccess performanceDB;

    @Before
    public void createPerformanceDB()
    {
        Context context = InstrumentationRegistry.getTargetContext();// ApplicationProvider.getApplicationContext();
        performanceDB = Room.inMemoryDatabaseBuilder(context, performanceDBAccess.class).build();
    }

    @After
    public void closePerformanceDB()
    {
        performanceDB.close();
    }

    @Test
    public void checkPerformanceDBEmpty()
    {
        performanceDBLogic test = new performanceDBLogic(performanceDB);

        test.checkDataInDatabase();

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);
    }

    @Test
    public void getAllPerformance()
    {
        performanceDBLogic test = new performanceDBLogic(performanceDB);

        test.checkDataInDatabase();

        Data performanceData = test.getAllPerformance();

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);

        int expectedLegPerformance = 3;//bad performance
        int legResult = performanceData.getInt("leg",0);
        assertEquals(expectedLegPerformance, legResult);

        int expectedArmPerformance = 3;//bad performance
        int armResult = performanceData.getInt("leg",0);
        assertEquals(expectedArmPerformance, armResult);

        int expectedChestPerformance = 3;//bad performance
        int chestResult = performanceData.getInt("leg",0);
        assertEquals(expectedChestPerformance, chestResult);


    }

    @Test
    public void addLegPerformance()
    {

        performanceDBLogic test = new performanceDBLogic(performanceDB);

        test.increasePerformance("leg");

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);
    }

    @Test
    public void addArmPerformance()
    {
        performanceDBLogic test = new performanceDBLogic(performanceDB);

        test.increasePerformance("arm");

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);
    }

    @Test
    public void addChestPerformance()
    {
        performanceDBLogic test = new performanceDBLogic(performanceDB);

        test.increasePerformance("chest");

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);
    }



    @Test
    public void calculatePerformance()
    {
        performanceDBLogic test = new performanceDBLogic(performanceDB);

        //in case the data base is empty
        test.checkDataInDatabase();

        /*it is expected that a person would have good performance
        if they do five exercises on one part of the body
         */

        int expect = 1; //means good performance
        int result = test.calculatePerformance(5);

        assertEquals(expect, result);
    }

    @Test
    public void getDuration()
    {
        performanceDBLogic test = new performanceDBLogic(performanceDB);

        long startTime = Calendar.getInstance().getTime().getTime();

        long expected = 0;
        long result = test.getDuration(startTime);

        assertEquals(expected, result);
    }


    private weightDBAccess weightDB;

    @Before
    public void createWeightDB()
    {
        Context context = InstrumentationRegistry.getTargetContext();// ApplicationProvider.getApplicationContext();
        weightDB = Room.inMemoryDatabaseBuilder(context, weightDBAccess.class).build();
    }

    @After
    public void closeWeightDB()
    {
        weightDB.close();
    }

    @Test
    public void addWeightToDataBase()
    {
        WeightDBLogic test = new WeightDBLogic(weightDB);

        test.addWeight(22);

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);

    }

    @Test
    public void getWeightFromDataBase()
    {
        WeightDBLogic test = new WeightDBLogic(weightDB);

        test.addWeight(22);

        Data foundWeight = test.getStoredWeight();

        boolean expected = true;
        boolean result = test.Successful();

        assertEquals(expected, result);

        double expectedStartWeight = 22;
        double startWeightResult = foundWeight.getDouble("start", 0);

        assertEquals(expectedStartWeight, startWeightResult);

        double expectedCurrentWeight = 22;
        double currentWeightResult = foundWeight.getDouble("current", 0);

        assertEquals(expectedCurrentWeight, currentWeightResult);

    }







}
