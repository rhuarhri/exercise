package com.example.rhuarhri.androidexerciseapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBAccess;

import com.example.rhuarhri.androidexerciseapp.internalDatabase.chosenExerciseDBLogic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.rhuarhri.androidexerciseapp", appContext.getPackageName());
    }

    private chosenExerciseDBAccess ExerciseDB;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();// ApplicationProvider.getApplicationContext();
        ExerciseDB = Room.inMemoryDatabaseBuilder(context, chosenExerciseDBAccess.class).build();
    }

    @After
    public void closeDb() throws IOException {
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



}
