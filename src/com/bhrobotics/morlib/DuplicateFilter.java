package com.bhrobotics.morlib;

public class DuplicateFilter extends EventEmitter implements Listener {
    private EventEmitter targetEmitter;
    
    public void handle(Event event) {
        if (targetEmitter != null) {
            trigger(event);
        }
    }
    
    public void setTargetEmitter(EventEmitter e) {
        targetEmitter = e;
    }
}