package com.bhrobotics.morlib;

import java.util.Hashtable;

public class Event {
    private String name;
    private Hashtable data;
    
    public Event(String n, Hashtable d) {
        name = n;
        data = d;
    }
    
    public String getName() {
        return name;
    }
    
    public Hashtable getData() {
        return data;
    }
    
    public Object getData(String key) {
        return data.get(key);
    }
}