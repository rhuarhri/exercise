package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class chosenExercises {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "type")
    String exerciseType;

    @ColumnInfo(name = "image")
    String imageName;

    @ColumnInfo(name = "min")
    int MinimumPerformanceLevel;

    @ColumnInfo(name = "max")
    int MaximumPerformanceLevel;

    @ColumnInfo(name = "amount")
    int amount;

    @NonNull
    public int getId() {
        return id;
    }

    public String getExerciseType() {
        return exerciseType;
    }

    public String getImageName() {
        return imageName;
    }

    public int getMinimumPerformanceLevel() {
        return MinimumPerformanceLevel;
    }

    public int getMaximumPerformanceLevel() {
        return MaximumPerformanceLevel;
    }

    public int getAmount() {
        return amount;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setMinimumPerformanceLevel(int minimumPerformanceLevel) {
        MinimumPerformanceLevel = minimumPerformanceLevel;
    }

    public void setMaximumPerformanceLevel(int maximumPerformanceLevel) {
        MaximumPerformanceLevel = maximumPerformanceLevel;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
