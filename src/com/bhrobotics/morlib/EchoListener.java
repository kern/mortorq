package com.bhrobotics.morlib;

public class EchoListener implements Listener {
    private boolean triggered = false;
    
    public void handle(Event event) {
        System.out.println("Event received: " + event.getName());
        triggered = true;
    }
    
    public boolean wasTriggered() {
        return triggered;
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
}