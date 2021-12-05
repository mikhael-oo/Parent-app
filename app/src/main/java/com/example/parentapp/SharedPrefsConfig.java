package com.example.parentapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.EditText;

import com.example.parentapp.models.Coin;
import com.example.parentapp.models.Kid;
import com.example.parentapp.models.KidManager;
import com.example.parentapp.models.Task;
import com.example.parentapp.models.TaskManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * This class handls all data storage and retrieval form shared preferences
 */
public class SharedPrefsConfig {

    public static final String COIN_HISTORY_SHAREDPREF_KEY = "History_Key";
    public static final String COIN_QUEUE_SHAREDPREF_KEY = "Coin_Queue_Key";
    public static final String TASK_LIST_SHAREDPREF_KEY = "Task_List_Key";
    private static final String KID_MANAGER_SHAREDPREF_KEY = "Kid_List_Key";
    private static final String KID_IMAGE_SHAREDPREF_KEY = "kid_image_key";
    private static final String BREATH_SHAREDPREF_KEY = "breath_key";


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
        String taskListString = prefs.getString(TASK_LIST_SHAREDPREF_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Task>>()    {}.getType();
        ArrayList<Task> savedTasks = gson.fromJson(taskListString, type);
        return savedTasks;
    }


    public static ArrayList<Kid> getKidsSharedPrefsData(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String kidsJsonString = prefs.getString(KID_MANAGER_SHAREDPREF_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Kid>>()    {}.getType();
        ArrayList<Kid> savedKids = gson.fromJson(kidsJsonString, type);
        return savedKids;
    }


    public static ArrayList<String> getKidImageSharedPrefsData(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String imageJsonString = prefs.getString(KID_IMAGE_SHAREDPREF_KEY, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>()  {}.getType();
        ArrayList<String> savedImages = gson.fromJson(imageJsonString, type);
        return savedImages;
    }


    public static int getBreathPrefsSharedPrefs(Context context) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int numBreaths = prefs.getInt(BREATH_SHAREDPREF_KEY, 0);
        return numBreaths;
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


    public static void setSavedTasksSharedPrefs(Context context, TaskManager taskmanager) {

        Gson gson = new Gson();
        String taskListString = gson.toJson(taskmanager.tasks);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefsConfig.TASK_LIST_SHAREDPREF_KEY, taskListString);
        editor.apply();
    }


    public static void setSavedKidsSharedPrefs(Context context, KidManager manager) {

        Gson gson = new Gson();
        String kidListString = gson.toJson(manager.kids);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefsConfig.KID_MANAGER_SHAREDPREF_KEY, kidListString);
        editor.apply();
    }



    public static void setKidPictureSharedPref(Context context, KidManager manager) {

        ArrayList<String> images = new ArrayList<>(0);
        for(Kid i: manager) {

            Bitmap img = i.getImage();
            java.io.ByteArrayOutputStream boas = new java.io.ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 100, boas);
            byte[] imgBytes = boas.toByteArray();
            String imgString = android.util.Base64.encodeToString(imgBytes, Base64.DEFAULT);
            images.add(imgString);
        }

        Gson gson = new Gson();
        String kidImageList = gson.toJson(images);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SharedPrefsConfig.KID_IMAGE_SHAREDPREF_KEY, kidImageList);
        editor.apply();
    }


    public static void setBreathPrefsSharedPrefs(Context context, int num) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SharedPrefsConfig.BREATH_SHAREDPREF_KEY, num);
        editor.apply();
    }
}
