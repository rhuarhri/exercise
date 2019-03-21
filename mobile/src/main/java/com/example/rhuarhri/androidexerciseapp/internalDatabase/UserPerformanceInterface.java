package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface UserPerformanceInterface {

    @Update
    void UpdatePerformance(storedUserPerformance newPerformance);

    @Insert
    void AddNewPerformance(storedUserPerformance newPerformance);

    @Query("SELECT COUNT(*) FROM storedUserPerformance")
    int databaseSize();

    @Query("SELECT * FROM storedUserPerformance")
    List<storedUserPerformance> getAll();

    @Query("UPDATE storedUserPerformance SET leg =:performance ")
    void increaseLegPerformance(int performance);

    @Query("SELECT leg FROM storedUserPerformance")
    List<Integer> getLegPerformance();

    @Query("UPDATE storedUserPerformance SET arm = :performance")
    void increaseArmPerformance(int performance);

    @Query("SELECT arm FROM storedUserPerformance")
    List<Integer> getArmPerformance();


    @Query("UPDATE storedUserPerformance SET chest = :performance")
    void increaseChestPerformance(int performance);

    @Query("SELECT chest FROM storedUserPerformance")
    List<Integer> getchestPerformance();

    @Query("SELECT duration FROM storedUserPerformance")
    List<Long> getStartTime();



}
