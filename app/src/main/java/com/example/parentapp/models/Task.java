package com.example.parentapp.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
task class that holds singular tasks  for the children to do
 */
public class Task {
    String taskName;
    String taskKid;
    Bitmap taskKidImage;
    KidManager manager = KidManager.getInstance();
    Random randomChild = new Random();
    private int nextChild;

    public List<Kid> historyManager = new ArrayList<>();

    public Task(String startName) {
        taskName = startName;
        if (manager.returnKids().isEmpty()) {
            taskKid = "No Child to assign to";
            taskKidImage = null;
            return;
        } else {
            nextChild = randomChild.nextInt(manager.returnKids().size());
            taskKid = manager.returnKids().get(nextChild).getName();
            taskKidImage = manager.returnKids().get(nextChild).getImage();
            Kid newHistory = new Kid(taskKid, taskKidImage);
            historyManager.add(newHistory);
        }

    }


    public void nextAssignee() {
        if (manager.returnKids().isEmpty()) {
            taskKid = "No Child to assign to";
            return;
        }
        try {
            nextChild += 1;
            taskKid = manager.returnKids().get(nextChild).getName();
            taskKidImage = manager.returnKids().get(nextChild).getImage();
            Kid newHistory = new Kid(taskKid, taskKidImage);
            historyManager.add(newHistory);
        } catch (IndexOutOfBoundsException e) {
            nextChild = 0;
            taskKid = manager.returnKids().get(0).getName();
            taskKidImage = manager.returnKids().get(0).getImage();
        }

    }

    public void setTaskName(String newName) {
        this.taskName = newName;
    }

    public void setTaskKid(String newKid) {
        this.taskKid = newKid;
    }

    public void setTaskKidImage(Bitmap newImage) {
        this.taskKidImage = newImage;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskKid() {
        return taskKid;
    }

    public Bitmap getTaskKidImage() {
        return taskKidImage;
    }

    public List<Kid> returnTaskHistory() {
        return historyManager;
    }

    private static Task taskInstance = null;

    private Task() {
    }

    public static Task getInstance() {
        if (taskInstance == null) {
            taskInstance = new Task();
        }
        return taskInstance;
    }

    //@Override
    public Iterator<Kid> iterator() {
        return historyManager.iterator();
    }
}


