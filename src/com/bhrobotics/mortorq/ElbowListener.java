package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class ElbowListener implements Listener {
    private static final int SLOT = 2;
    private static final int CHANNEL = 2;
    private Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public void lower() {
        solenoid.set(false);
    }
    
    public void raise() {
        solenoid.set(true);
    }
    
    public void handle(Event event) {
        lower();
    }
    
    public void bound(String event, EventEmitter emitter) {}
    public void unbound(String event, EventEmitter emitter) {}
}