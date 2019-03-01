package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface chosenExerciseInterface {

    @Insert
    void addExercise(chosenExercises newExercise);

    @Query("DELETE FROM chosenExercises")
    void deleteAll();

    @Query("SELECT * FROM chosenExercises")
    List<chosenExercises> getAll();


}
