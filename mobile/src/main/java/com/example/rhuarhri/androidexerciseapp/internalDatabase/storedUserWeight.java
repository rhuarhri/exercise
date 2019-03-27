package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/*
room entity class which represents an SQL table
provided by
android room library part of the android jet pack library collection
*/

@Entity
public class storedUserWeight {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "start")
    double startWeight;

    @ColumnInfo(name = "current")
    double currentWeight;

    //getters and setters

    @NonNull
    public int getId() {
        return id;
    }

    public double getStartWeight() {
        return startWeight;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setStartWeight(double startWeight) {
        this.startWeight = startWeight;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }
}
