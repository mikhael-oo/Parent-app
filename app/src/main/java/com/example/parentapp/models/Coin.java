package com.example.parentapp.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Coin class, represents a fair coin with head and tail
 * It will be used in parent support app to be used by kids for various purposes
 * Singleton model class
 */

public class Coin {

    private ArrayList<String[]> history;


    private static Coin instance = null;

    private Coin()  {
        history = new ArrayList<>(0);
    }

    public static Coin getCoinInstance()    {

        if(instance == null)   {
            instance = new Coin();
        }
        return instance;
    }


    public boolean isHistoryEmpty() {
        return history.isEmpty();
    }

    public String[] getRecord(int i)    {
        return history.get(i);
    }


    public String[] getLastRecord() {
        if (!isHistoryEmpty()) {
            return history.get(history.size() - 1);
        }
        return null;
    }




    // return true for head, false for tail
    public boolean toss()   {

        int toss = (int) (Math.random() + 0.5);
        return toss == 1;
    }


    public void addToHistory(String kidName, String kidChoice, String result) {

        String[] newEntry = new String[4];
        newEntry[0] = kidName;
        newEntry[1] = kidChoice;

        DateFormat df = new SimpleDateFormat("MMM dd @ hh:mm");
        Date now = Calendar.getInstance().getTime();
        newEntry[2] = df.format(now);
        newEntry[3] = result;
        history.add(newEntry);
    }



    public void editHistory(String orgName, String newName) {

        for(String[] kid : history)   {
            if(kid[0].equalsIgnoreCase(orgName)) {
                kid[0] = newName;
            }
        }
    }



    public void deleteFromHistory(String kidName)   {

        for(int i = 0; i < history.size(); i++) {
            if(history.get(i)[0].equalsIgnoreCase(kidName)) {
                history.remove(i);
                i--;
            }
        }
    }

}
