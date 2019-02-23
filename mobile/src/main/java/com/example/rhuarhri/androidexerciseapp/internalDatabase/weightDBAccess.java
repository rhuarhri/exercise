package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/*
room access class
provided by
android room library part of the android jet pack library collection
*/

@Database(entities = {storedUserWeight.class}, version = 1)
public abstract class weightDBAccess extends RoomDatabase {

    public abstract UserWeightInterface storedWeight();
}
