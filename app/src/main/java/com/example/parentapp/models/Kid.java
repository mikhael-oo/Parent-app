package com.example.parentapp.models;

import android.graphics.Bitmap;

/**
Class designed to model child in Practical Parent.
Filled with simple getters and setters of names and age of child.
 */
public class Kid {
    String name;
    Bitmap image;

    public Kid(String startName, Bitmap startImage) {
        name = startName;
        image = startImage;
    }

    public String getName() {
        return this.name;
    }
    public Bitmap getImage() { return this.image; }

    public void setName(String newName) {
        this.name = newName;
    }
    public void setImage(Bitmap newImage) { this.image = newImage; }

}
