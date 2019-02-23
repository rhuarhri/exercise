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

    @ColumnInfo(name = "performance")
    String RequiredPerformanceLevel;

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

    public String getRequiredPerformanceLevel() {
        return RequiredPerformanceLevel;
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

    public void setRequiredPerformanceLevel(String requiredPerformanceLevel) {
        RequiredPerformanceLevel = requiredPerformanceLevel;
    }
}
