package com.example.parentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.Task;
import com.example.parentapp.models.TaskManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefsConfig {

    public static final String COIN_HISTORY_SHAREDPREF_KEY = "History_Key";
    public static final String COIN_QUEUE_SHAREDPREF_KEY = "Coin_Queue_Key";
    private Coin coin = Coin.getCoinInstance();
    private TaskManager tasks = TaskManager.getInstance();


    public static ArrayList<String[]> getCoinHistorySharedPrefsData(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String historyJsonString = prefs.getString(COIN_HISTORY_SHAREDPREF_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String[]>>()    {}.getType();
        ArrayList<String[]> savedHistory = gson.fromJson(historyJsonString, type);
        return savedHistory;
    }


    public static ArrayList<Kid> getCoinQueueSharedPrefsData(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String queueJsonString = prefs.getString(COIN_QUEUE_SHAREDPREF_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Kid>>()    {}.getType();
        ArrayList<Kid> savedQueue = gson.fromJson(queueJsonString, type);
        return savedQueue;
    }

    public static ArrayList<Task> getSavedTasksSharedPrefsData(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String queueJsonString = prefs.getString(COIN_QUEUE_SHAREDPREF_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Kid>>()    {}.getType();
        ArrayList<Kid> savedTasks = gson.fromJson(queueJsonString, type);
        return savedTasks;
    }



    //setters



    public static void setCoinHistorySharedPrefsData(Context context, Coin coin) {

        Gson gson = new Gson();
        String historyGsonString = gson.toJson(coin.getHistory());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefsConfig.COIN_HISTORY_SHAREDPREF_KEY, historyGsonString);
        editor.apply();
    }


    public static void setCoinQueueSharedPrefs(Context context, Coin coin) {

        Gson gson = new Gson();
        String queueGsonString = gson.toJson(coin.getTurnQueue());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefsConfig.COIN_QUEUE_SHAREDPREF_KEY, queueGsonString);
        editor.apply();
    }

    public static void setSavedTasksSharedPrefs(Context context, Coin coin) {

        Gson gson = new Gson();
        String queueGsonString = gson.toJson(coin.getTurnQueue());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefsConfig.COIN_QUEUE_SHAREDPREF_KEY, queueGsonString);
        editor.apply();
    }
}
