package com.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Vector;

public abstract class Filter implements Listener {
    public abstract EventEmitter getEmitter();
    
    public Hashtable getListeners() {
        return getEmitter().getListeners();
    }
    
    public Vector getListeners(String event) {
        return getEmitter().getListeners(event);
    }
    
    public Vector getListeners(String event, boolean includeAll) {
        return getEmitter().getListeners(event, includeAll);
    }
    
    public void bind(String event, Listener listener) {
        getEmitter().bind(event, listener);
    }
    
    public void unbind(String event, Listener listener) {
        getEmitter().unbind(event, listener);
    }
    
    public void trigger(String name) {
        getEmitter().trigger(name);
    }
    
    public void trigger(String name, Hashtable data) {
        getEmitter().trigger(name, data);
    }
    
    public void trigger(String name, boolean flush) {
        getEmitter().trigger(name, flush);
    }
    
    public void trigger(String name, Hashtable data, boolean flush) {
        getEmitter().trigger(name, data, flush);
    }
    
    public void trigger(Event event) {
        getEmitter().trigger(event);
    }
    
    public void trigger(Event event, boolean flush) {
        getEmitter().trigger(event, flush);
    }
}