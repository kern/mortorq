package com.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;

public class EventEmitter {
    private Hashtable listeners = new Hashtable();
    
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
    
    public void bind(String event, Listener listener) {
        Vector eventListeners = getListeners(event);
        eventListeners.addElement(listener);
        listener.bound(event, this);
    }
    
    public void unbind(String event, Listener listener) {
        Vector eventListeners = getListeners(event);
        eventListeners.removeElement(listener);
        listener.unbound(event, this);
    }
    
    public void trigger(String name) {
        trigger(new Event(name, new Hashtable()), false);
    }
    
    public void trigger(String name, Hashtable data) {
        trigger(new Event(name, data), false);
    }
    
    public void trigger(String name, boolean flush) {
        trigger(new Event(name, new Hashtable()), flush);
    }
    
    public void trigger(String name, Hashtable data, boolean flush) {
        trigger(new Event(name, data), flush);
    }
    
    public void trigger(Event event) {
        trigger(event, false);
    }
    
    public void trigger(Event event, boolean flush) {
        Vector eventListeners = getListeners(event.getName());
        
        Enumeration e = eventListeners.elements();
        while(e.hasMoreElements()) {
            Queue queue = Reactor.getInstance().getQueue();
            queue.addTail(event, (Listener) e.nextElement());
        }
        
        if(flush) {
            eventListeners.removeAllElements();
        }
    }
}