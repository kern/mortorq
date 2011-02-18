package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class ElbowListener implements Listener {
    private static final int SLOT    = 8;
    private static final int CHANNEL = 1;
    
    private static final boolean RAISED  = false;
    private static final boolean LOWERED = true;
    private static final boolean DEFAULT = RAISED;
    
    private Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public ElbowListener() {
        reset();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("elbowRaise")) {
            raise();
        } else if (name.equals("elbowLower")) {
            lower();
        } else if (name.equals("elbowToggle")) {
            toggle();
        } else if (name.equals("elbowReset")) {
            reset();
        }
    }

    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public boolean get() {
        return solenoid.get();
    }
    
    public void set(boolean value) {
        solenoid.set(value);
    }
    
    public void toggle() {
        set(!get());
    }
    
    public void reset() {
        set(DEFAULT);
    }
    
    public void lower() {
        set(LOWERED);
    }
    
    public void raise() {
        set(RAISED);
    }
}
