package com.example.parentapp.models;

import java.time.LocalDateTime;

/*
Class designed to model child in Practical Parent.
Filled with simple getters and setters of names and age of child.
 */
public class Kid {
    String name;

    public Kid(String startName) {
        name = startName;
    }

    public String getName() {
        return this.name;
    }
    public void setName(String newName) {
        this.name = newName;
    }

}
