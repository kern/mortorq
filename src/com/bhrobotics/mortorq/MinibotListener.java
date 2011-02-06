package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class MinibotListener implements Listener {
    private static final int SLOT    = 1;
    private static final int CHANNEL = 1;
    private Solenoid solenoid        = new Solenoid(SLOT, CHANNEL);
    
    private boolean timedOut      = false;
    private boolean safetyEngaged = true;
    
    public MinibotListener() {
        reset();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name == "timeout") {
            timedOut = true;
        } else {
            safetyEngaged = false;
        }
        
        if (timedOut && !safetyEngaged) {
            timedOut = false;
            safetyEngaged = true;
            solenoid.set(true);
        }
    }
    
    public void bound(String event, EventEmitter emitter) {}
    public void unbound(String event, EventEmitter emitter) {}
    
    public void reset() {
        solenoid.set(false);
        timedOut = false;
        safetyEngaged = true;
    }
}