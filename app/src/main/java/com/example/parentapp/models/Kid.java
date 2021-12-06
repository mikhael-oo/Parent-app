package com.example.parentapp.models;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
Class designed to model child in Practical Parent.
Filled with simple getters and setters of names and age of child.
 */
public class Kid {
    String name;
    Bitmap image;
    LocalDateTime date;

    public Kid(String startName, Bitmap startImage) {
        name = startName;
        image = startImage;
    }

    public String getName() {
        return this.name;
    }
    public Bitmap getImage() { return this.image; }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setDate(){
        date = LocalDateTime.now();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDate(){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = date.format(format);
        return formattedDate;
    }

    public void setName(String newName) {
        this.name = newName;
    }
    public void setImage(Bitmap newImage) { this.image = newImage; }

}
