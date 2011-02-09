package com.bhrobotics.morlib;

public class HelloListener implements Listener {
    private boolean triggered = false;
    
    public void handle(Event event) {
        System.out.println("Hello world!");
        triggered = true;
    }
    
    public boolean wasTriggered() {
        return triggered;
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
}