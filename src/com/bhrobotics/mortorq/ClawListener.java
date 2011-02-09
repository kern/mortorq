package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class ClawListener implements Listener {
    private static final int SLOT = 1;
    private static final int CHANNEL = 1;
    Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public ClawListener() {
        reset();
    }
    
    public void handle(Event event) {
        solenoid.set(!solenoid.get());
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public void reset() {
        solenoid.set(false);
    }
}