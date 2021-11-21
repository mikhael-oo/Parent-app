package com.example.parentapp.models;

import java.util.Random;

/*
task class that holds singular tasks  for the children to do
 */
public class Task {
    String taskName;
    String taskKid;
    KidManager manager = KidManager.getInstance();
    Random randomChild = new Random();
    private int nextChild;
    private String firstAssignee;

    public Task(String startName) {
        taskName = startName;
        if(manager.returnKids().isEmpty()) {
            taskKid = "No Child to assign to";
            return;
        }
        else {
            nextChild = randomChild.nextInt(manager.returnKids().size());
            taskKid = manager.returnKids().get(nextChild).getName();
//            if((manager.returnKids().get(0)) != null) {
//                firstAssignee = taskKid;
//            }
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
        } catch (IndexOutOfBoundsException e) {
            nextChild = -1;
            taskKid = manager.returnKids().get(0).getName();
        }

    }

    public void setTaskName(String newName) {
        this.taskName = newName;
    }
    public void setTaskKid(String newKid) {
        this.taskKid = newKid;
    }

    public String getTaskName() {
        return taskName;
    }
    public String getTaskKid() {
        return taskKid;
    }

}
