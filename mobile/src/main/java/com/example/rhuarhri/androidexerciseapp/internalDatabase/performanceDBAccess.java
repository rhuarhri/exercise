package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {storedUserPerformance.class}, version = 1, exportSchema = false)
public abstract class performanceDBAccess extends RoomDatabase{

    public abstract UserPerformanceInterface storedPerformance();
}
