package com.bhrobotics.morlib;

import com.bhrobotics.morlib.Filter;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import java.util.Hashtable;

public class RenameFilter extends Filter {
    private Hashtable links = new Hashtable();
    private EventEmitter emitter = new EventEmitter(); 
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void bound (EventEmitter em, String event) {}
    public void unbound (EventEmitter em, String event) {}
    
    public void addLink(String occur, Event effect) {
        links.put(occur, effect);
    }
    
    public void addLink(String occur, String effect) {
        links.put(occur, effect);
    }
    
    public Hashtable getLinks() {
        return links;
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (links.containsKey(name)) {
            Object linkObj = links.get(name);
            String linkClass = linkObj.getClass().getName();
            
            if (linkClass.equals("java.lang.String")) {
                trigger((String) linkObj, event.getData());
            } else if (linkClass.equals("com.bhrobotics.morlib.Event")) {
                trigger((Event) linkObj);
            }
        } else {
            trigger(event);
        }
    }
}