package com.example.parentapp.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

