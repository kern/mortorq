package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class WristListener implements Listener {
    private static final int SLOT    = 1;
    private static final int CHANNEL = 1;
    
    private static final boolean RAISED  = false;
    private static final boolean LOWERED = true;
    private static final boolean DEFAULT = RAISED;
    
    Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public WristListener() {
        reset();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name == "wristRaise") {
            raise();
        } else if (name == "wristLower") {
            lower();
        } else if (name == "wristToggle") {
            toggle();
        } else if (name == "wristReset") {
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
    
    public void raise() {
        set(RAISED);
    }
    
    public void lower() {
        set(LOWERED);
    }
}