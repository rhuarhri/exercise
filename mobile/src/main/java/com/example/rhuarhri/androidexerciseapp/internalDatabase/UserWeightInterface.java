package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/*
room interface
provided by
android room library part of the android jet pack library collection
*/
@Dao
public interface UserWeightInterface {


    @Insert
    void add(storedUserWeight newWeight);

    @Query("SELECT COUNT(id) FROM storedUserWeight")
    int getEnteryAmount();

    @Query("UPDATE storedUserWeight SET  current = :newCurrentWeight")
    void addCurrentWeight(double newCurrentWeight);

    @Query("SELECT * FROM storedUserWeight")
    List<storedUserWeight> getAll();

    //does not need to delete as the only time that it would necessary
    //to delete this data is when the app is being uninstalled

}
