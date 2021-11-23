package com.example.parentapp.models;

import android.graphics.Bitmap;

import java.util.Random;

/*
task class that holds singular tasks  for the children to do
 */
public class Task {
    String taskName;
    String taskKid;
    Bitmap taskKidImage;
    KidManager manager = KidManager.getInstance();
    Random randomChild = new Random();
    private int nextChild;

    public Task(String startName) {
        taskName = startName;
        if(manager.returnKids().isEmpty()) {
            taskKid = "No Child to assign to";
            taskKidImage = null;
            return;
        }
        else {
            nextChild = randomChild.nextInt(manager.returnKids().size());
            taskKid = manager.returnKids().get(nextChild).getName();
            taskKidImage = manager.returnKids().get(nextChild).getImage();
        }

    }

    public void nextAssignee() {
        if(manager.returnKids().isEmpty()) {
            taskKid = "No Child to assign to";
            return;
        }
        try {
            nextChild += 1;
            taskKid = manager.returnKids().get(nextChild).getName();
            taskKidImage = manager.returnKids().get(nextChild).getImage();
        } catch (IndexOutOfBoundsException e) {
            nextChild = -1;
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
    public void setTaskKidImage(Bitmap newImage) {this.taskKidImage = newImage;}

    public String getTaskName() {
        return taskName;
    }
    public String getTaskKid() {
        return taskKid;
    }

    public Bitmap getTaskKidImage() {
        return taskKidImage;
    }
}
