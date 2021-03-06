package com.example.parentapp.models;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
kid manager class which allows us to add a child, edit it, or even delete it
implements singleton support
 */
public class KidManager implements Iterable<Kid>{
    public List<Kid> kids = new ArrayList<>();


    public void addKid(Kid newKid) {
        kids.add(newKid);
    }

    public void removeKid(int i) {
        kids.remove(i);
    }

    public List<Kid> returnKids() {
        return kids;
    }

    public boolean search(String name)   {

        if(name == null) return false;

        for(int i = 0; i < kids.size(); i++)    {
            if(kids.get(i).getName().equalsIgnoreCase(name))    return true;
        }
        return false;
    }


    private static KidManager managerInstance = null;

    private KidManager() {}

    public static KidManager getInstance(){
        if(managerInstance == null) {
            managerInstance = new KidManager();
        }
        return managerInstance;
    }

    @NonNull
    @Override
    public Iterator<Kid> iterator() {
        return kids.iterator();
    }
}
