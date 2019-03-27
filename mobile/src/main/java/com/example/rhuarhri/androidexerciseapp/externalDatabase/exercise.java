package com.example.rhuarhri.androidexerciseapp.externalDatabase;

public class exercise {

    String name;
    String type;
    String image;
    int minperformance;
    int maxperformance;
    long time;

    //getters and setters

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getImage() {
        return image;
    }

    public int getMinperformance() {
        return minperformance;
    }

    public int getMaxperformance() {
        return maxperformance;
    }

    public long getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setMinperformance(int minperformance) {
        this.minperformance = minperformance;
    }

    public void setMaxperformance(int maxperformance) {
        this.maxperformance = maxperformance;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
