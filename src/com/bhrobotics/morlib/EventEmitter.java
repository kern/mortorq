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
        return getListeners(event, false);
    }
    
    public Vector getListeners(String event, boolean includeAll) {
        Vector v;
        
        if (!includeAll || event == "all") {
            if (listeners.containsKey(event)) {
                v = (Vector) listeners.get(event);
            } else {
                v = new Vector();
                listeners.put(event, v);
            }
        } else {
            v = new Vector();
            Vector specificListener = getListeners(event);
            Vector allListeners = getListeners("all");
            
            Enumeration e = specificListener.elements();
            while(e.hasMoreElements()) {
                v.addElement(e.nextElement());
            }
            
            e = allListeners.elements();
            while(e.hasMoreElements()) {
                v.addElement(e.nextElement());
            }
        }
        
        return v;
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
        Queue queue = Reactor.getInstance().getQueue();
        Vector eventListeners = getListeners(event.getName(), true);
        
        Enumeration e = eventListeners.elements();
        while(e.hasMoreElements()) {
            Listener asdf = (Listener) e.nextElement();
            queue.addTail(event, asdf);
        }
        
        if(flush && !eventListeners.isEmpty()) {
            getListeners(event.getName()).removeAllElements();
        }
    }
}