package com.example.parentapp.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This Class manages a list of Tasks which will be assigned to a single kid
 */
public class TaskManager {
    public List<Task> tasks = new ArrayList<>();


    public void addTask(Task newTask) {
        tasks.add(newTask);
    }

    public void removeTask(int i) {
        tasks.remove(i);
    }

    public List<Task> returnTasks() {
        return tasks;
    }

    public void editTasks(String orgName, String newName) {

        if(orgName != null && newName != null) {
            for (Task task : tasks) {
                if (task.taskKid.equalsIgnoreCase(orgName)) {
                    task.taskKid = newName;
                }
            }
        }
    }


    public void deleteFromTasks(String kidName) {

        if (kidName != null) {
            for (Task t : tasks) {
                for (Kid k : t.returnTaskHistory()) {
                    if (k.getName().equalsIgnoreCase(kidName)) {
                        k.setName("Kid Deleted");
                    }
                }
            }
        }
    }


    public void deleteFromHistory(String kidName)   {

        if(kidName != null) {
            for(int i = 0; i < tasks.size(); i++) {
                if(tasks.get(i).returnTaskHistory().get(i).getName().equalsIgnoreCase(kidName))     {
                    tasks.get(i).returnTaskHistory().get(i).setName("Kid Deleted");
                }
            }
        }
    }



    private static TaskManager managerInstance = null;

    private TaskManager() {}

    public static TaskManager getInstance(){
        if(managerInstance == null) {
            managerInstance = new TaskManager();
        }
        return managerInstance;
    }



//    @Override
    public Iterator<Task> iterator() {
        return tasks.iterator();
    }
}

