package com.example.parentapp.models;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Coin class, represents a fair coin with head and tail
 * It will be used in parent support app to be used by kids for various purposes
 * Singleton model class
 */

public class Coin {

    private final ArrayList<String[]> history;
    private final ArrayList<Kid> coinTurns;


    private static Coin instance = null;


    private Coin()  {
        history = new ArrayList<>(0);
        coinTurns = new ArrayList<>(0);
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


    public List<Kid> getCoinTurns() {
        return coinTurns;
    }


    public Kid getTurnKid() {
        return getCoinTurns().get(0);
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
        deleteFromTurns(kidName);
    }


    public void deleteFromTurns(String kidName)   {

        for(int i = 0; i < coinTurns.size(); i++)   {
            if(coinTurns.get(i).getName().equalsIgnoreCase(kidName))    {
                coinTurns.remove(i);
                i--;
            }
        }
    }


    public void updateTurns()    {

        for(Kid i : KidManager.getInstance().kids)    {
            if(!searchTurns(i.getName())) {
                coinTurns.add(i);
            }
        }
    }


    public void kidFlippedCoin(String kidName)    {

        int indexOfKid = 0;
        for(int i = 0; i < coinTurns.size(); i++)   {
            if(coinTurns.get(i).getName().equals(kidName))    {
                indexOfKid = i;
            }
        }
        Kid sameKid = new Kid(kidName);
        coinTurns.add(sameKid);
        coinTurns.remove(indexOfKid);
    }



    public boolean searchTurns(String kidName)  {

        if(!coinTurns.isEmpty()) {
            for(Kid i : coinTurns)  {
                if(i.getName().equalsIgnoreCase(kidName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
