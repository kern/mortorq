package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class ClawListener implements Listener {
    private static final int SLOT    = 8;
    private static final int CHANNEL = 3;
    
    private static final boolean WIDE    = false;
    private static final boolean NARROW  = true;
    private static final boolean DEFAULT = WIDE;
    
    Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public ClawListener() {
        reset();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("clawNarrow")) {
            narrow();
        } else if (name.equals("clawWide")) {
            wide();
        } else if (name.equals("clawToggle")) {
            toggle();
        } else if (name.equals("clawReset")) {
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
    
    public void narrow() {
        set(NARROW);
    }
    
    public void wide() {
        set(WIDE);
    }
}
