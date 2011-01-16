package com.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

public class EventEmitter {
    private Queue queue;
    private Hashtable listeners = new Hashtable();
    
    public EventEmitter(Queue q) {
        queue = q;
    }
    
    public Hashtable getListeners() {
        return listeners;
    }
    
    public Vector getListeners(String event) {
        if(listeners.containsKey(event)) {
            return (Vector) listeners.get(event);
        } else {
            Vector v = new Vector();
            listeners.put(event, v);
            return v;
        }
    }
    
    public void addListener(String event, Listener listener) {
        Vector eventListeners = getListeners(event);
        eventListeners.addElement(listener);
    }
    
    public void removeListener(String event, Listener listener) {
        Vector eventListeners = getListeners(event);
        eventListeners.removeElement(listener);
    }
    
    public void emit(String name) {
        emit(new Event(name, new Hashtable()), false);
    }
    
    public void emit(String name, Hashtable data) {
        emit(new Event(name, data), false);
    }
    
    public void emit(String name, boolean flush) {
        emit(new Event(name, new Hashtable()), flush);
    }
    
    public void emit(String name, Hashtable data, boolean flush) {
        emit(new Event(name, data), flush);
    }
    
    public void emit(Event event) {
        emit(event, false);
    }
    
    public void emit(Event event, boolean flush) {
        Vector eventListeners = getListeners(event.getName());
        
        Enumeration e = eventListeners.elements();
        while(e.hasMoreElements()) {
            queue.addTail(event, (Listener) e.nextElement());
        }
        
        if(flush) {
            eventListeners.removeAllElements();
        }
    }
}