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
    private Task firstAssignee;

    public Task(String startName) {
        taskName = startName;
        if(manager.returnKids().isEmpty()) {
            taskKid = "No Child to assign to";
            return;
        }
        else {
            nextChild = randomChild.nextInt(manager.returnKids().size());
            taskKid = manager.returnKids().get(nextChild).getName();
            if(manager.returnKids().isEmpty()) {
                firstAssignee = new Task(manager.returnKids().get(nextChild).getName());
            }
        }

    }
    public void nextAssignee() {
        if(manager.returnKids().isEmpty()) {
            taskKid = "No Child to assign to";
            return;
        }
        if(manager.returnKids().get(nextChild+1) == null) {
            taskKid = firstAssignee.getTaskKid();
        }
            nextChild += 1;
            taskKid = manager.returnKids().get(nextChild).getName();

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
