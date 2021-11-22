package com.example.parentapp.models;

import android.graphics.Bitmap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Coin class, represents a fair coin with head and tail
 * It will be used in parent support app to be used by kids for various purposes
 * Singleton model class
 */

public class Coin {

    private final ArrayList<String[]> history;
    private final ArrayList<Kid> turnQueue;


    private static Coin instance = null;


    private Coin()  {
        history = new ArrayList<>(0);
        turnQueue = new ArrayList<>(0);
    }


    public static Coin getCoinInstance()    {

        if(instance == null)   {
            instance = new Coin();
        }
        return instance;
    }


    public ArrayList<String[]> getHistory() {
        return history;
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


    public List<Kid> getTurnQueue() {
        return turnQueue;
    }


    public Kid getTurnKid() {
        return getTurnQueue().get(0);
    }

    public boolean isHistoryEmpty() {
        return history.isEmpty();
    }

    public int historyLength()  {
        return history.size();
    }

    public int recordLength()   {
        return history.get(0).length;
    }



    public boolean toss()   {

        int toss = (int) (Math.random() + 0.5);
        return toss == 1;
    }


    public void addToHistory(String kidName, String kidChoice, String result) {

        if(kidName != null && kidChoice != null && result != null) {
            String[] newEntry = new String[4];
            newEntry[0] = kidName;
            newEntry[1] = kidChoice;

            DateFormat df = new SimpleDateFormat("MMM dd @ hh:mm");
            Date now = Calendar.getInstance().getTime();
            newEntry[2] = df.format(now);
            newEntry[3] = result;
            history.add(newEntry);
        }
    }


    public void addToStartOfQueue(String kidName, Bitmap img)     {

        if(kidName != null) {
            Kid kid = new Kid(kidName, img);
            getTurnQueue().add(0, kid);
        }
    }



    public void editHistory(String orgName, String newName) {

        if(orgName != null && newName != null) {
            for (String[] kid : history) {
                if (kid[0].equalsIgnoreCase(orgName)) {
                    kid[0] = newName;
                }
            }
        }
    }


    public void deleteFromHistory(String kidName)   {

        if(kidName != null) {
            for (int i = 0; i < history.size(); i++) {
                if (history.get(i)[0].equalsIgnoreCase(kidName)) {
                    history.remove(i);
                    i--;
                }
            }
            deleteFromTurns(kidName);
        }
    }


    public void deleteFromTurns(String kidName)   {

        if(kidName != null) {
            for (int i = 0; i < turnQueue.size(); i++) {
                if (turnQueue.get(i).getName().equalsIgnoreCase(kidName)) {
                    turnQueue.remove(i);
                    i--;
                }
            }
        }
    }


    public void updateTurns()    {

        for(Kid i : KidManager.getInstance().kids)    {
            if(!searchTurns(i.getName())) {
                turnQueue.add(i);
            }
        }
    }


    public void kidFlippedCoin(String kidName, Bitmap img)    {

        if(kidName != null) {
            int indexOfKid = 0;
            for (int i = 0; i < turnQueue.size(); i++) {
                if (turnQueue.get(i).getName().equals(kidName)) {
                    indexOfKid = i;
                }
            }
            Kid sameKid = new Kid(kidName, img);
            turnQueue.add(sameKid);
            turnQueue.remove(indexOfKid);
        }
    }



    public boolean searchTurns(String kidName)  {

        if(!turnQueue.isEmpty() && kidName != null) {
            for(Kid i : turnQueue)  {
                if(i.getName().equalsIgnoreCase(kidName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
