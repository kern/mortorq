package com.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

public class EventEmitter {
    private Hashtable listeners = new Hashtable();
    private Timer timer = new Timer();
    
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
    }
    
    public void unbind(String event, Listener listener) {
        Vector eventListeners = getListeners(event);
        eventListeners.removeElement(listener);
    }
    
    public void unbindAll() {
        listeners = new Hashtable();
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
        Queue queue = Reactor.getQueue();
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
    
    public void schedule(String name, double delay) {
        schedule(name, new Hashtable(), delay);
    }
    
    public void schedule(String name, Hashtable data, double delay) {
        schedule(new Event(name, data), delay);
    }
    
    public void schedule(Event event, double delay) {
        TimeoutTask task = new TimeoutTask(event);
        timer.schedule(task, (long) (delay * 1000));
    }
    
    public void cancelAll() {
        timer.cancel();
        timer = new Timer();
    }
    
    private class TimeoutTask extends TimerTask {
        private Event event;
        
        public TimeoutTask(Event e) {
            event = e;
        }
        
        public void run() {
            trigger(event);
        }
    }
}