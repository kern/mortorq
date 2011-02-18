package com.bhrobotics.morlib;

public class DuplicateFilter extends Filter {
    private EventEmitter emitter;
    
    public void handle(Event event) {
        if (emitter != null) {
            trigger(event);
        }
    }
    
    public void bound(EventEmitter emitter, String name) {}
    public void unbound(EventEmitter emitter, String name) {}
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void setEmitter(EventEmitter e) {
        emitter = e;
    }
}