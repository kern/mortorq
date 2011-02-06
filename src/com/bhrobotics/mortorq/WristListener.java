package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class WristListener implements Listener{
    private static final int SLOT    = 1;
    private static final int CHANNEL = 1;
    
    Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public WristListener() {
        reset();
    }
    
    public void handle(Event event) {
        solenoid.set(!solenoid.get());
    }
    
    public void bound(String event, EventEmitter emitter) {}
    public void unbound(String event, EventEmitter emitter) {}
    
    public void reset() {
        solenoid.set(false);
    }
}