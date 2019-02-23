package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Database;

@Database(entities = {storedUserPerformance.class}, version = 1)
public abstract class performanceDBAccess {

    public abstract UserWeightInterface storedPerformance();
}
