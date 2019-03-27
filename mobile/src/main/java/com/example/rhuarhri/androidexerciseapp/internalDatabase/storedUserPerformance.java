package com.example.rhuarhri.androidexerciseapp.internalDatabase;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class storedUserPerformance {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "duration")
    long durationBaseFromCalender;

    @ColumnInfo(name = "leg")
    int timesExercisingLegs;

    @ColumnInfo(name = "arm")
    int timesExercisingArms;

    @ColumnInfo(name = "chest")
    int timesExercisingChest;

    //getters and setters

    @NonNull
    public int getId() {
        return id;
    }

    public long getDurationBaseFromCalender() {
        return durationBaseFromCalender;
    }

    public int getTimesExercisingLegs() {
        return timesExercisingLegs;
    }

    public int getTimesExercisingArms() {
        return timesExercisingArms;
    }

    public int getTimesExercisingChest() {
        return timesExercisingChest;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public void setDurationBaseFromCalender(long durationBaseFromCalender) {
        this.durationBaseFromCalender = durationBaseFromCalender;
    }

    public void setTimesExercisingLegs(int timesExercisingLegs) {
        this.timesExercisingLegs = timesExercisingLegs;
    }

    public void setTimesExercisingArms(int timesExercisingArms) {
        this.timesExercisingArms = timesExercisingArms;
    }

    public void setTimesExercisingChest(int timesExercisingChest) {
        this.timesExercisingChest = timesExercisingChest;
    }
}
