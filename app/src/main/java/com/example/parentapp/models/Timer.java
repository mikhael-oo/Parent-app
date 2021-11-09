package com.example.parentapp.models;

/**
 * Timer class which represents a timer
 */
public class Timer {
    private static Timer instance;

    private int minutes = 1;


    private Timer() {}

    public static Timer getTimerInstance() {
        if (instance == null) {
            instance = new Timer();
        }
        return instance;
    }

    public int getMinutes() {
        return this.minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
