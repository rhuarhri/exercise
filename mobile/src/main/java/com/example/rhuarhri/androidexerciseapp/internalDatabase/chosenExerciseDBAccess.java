package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {chosenExercises.class}, version = 1, exportSchema = false)
public abstract class chosenExerciseDBAccess extends RoomDatabase {

    public abstract chosenExerciseInterface storedExercises();


}
