package com.example.parentapp.models;

/**
 * Coin class, represents a fair coin with head and tail
 * It will be used in parent support app to be used by kids for various purposes
 * Singleton model class
 */

public class Coin {

    private static Coin instance = null;

    private Coin()  {}

    public static Coin getCoinInstance()    {

        if(instance == null)   {
            instance = new Coin();
        }
        return instance;
    }


    // return true for head, false for tail
    public boolean toss()   {

        int toss = (int) (Math.random() + 0.5);
        return toss == 1;
    }
}
