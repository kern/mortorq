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
}