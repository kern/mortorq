package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Solenoid;

public class MinibotListener implements Listener {
    private static final int SLOT    = 8;
    private static final int CHANNEL = 4;
    
    private static final boolean REDACTED = false;
    private static final boolean DEPLOYED = true;
    private static final boolean DEFAULT  = REDACTED;
    
    private Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    private boolean endGame       = false;
    private boolean safetyEngaged = true;
    
    public MinibotListener() {
        reset();
    }
    
    public void handle(Event event) {
        if (event.getName().equals("startEndGame")) {
            endGame = true;
            
            if (isReady()) {
                deploy();
            }
        } else if (event.getName().equals("disengageSafety")) {
            safetyEngaged = false;
            
            if (isReady()) {
                deploy();
            }
        } else if (event.getName().equals("minibotReset")) {
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
        endGame = false;
        safetyEngaged = true;
    }
    
    public void deploy() {
        set(DEPLOYED);
    }
    
    public void redact() {
        set(REDACTED);
    }
    
    public boolean isReady() {
        return endGame && !safetyEngaged;
    }
}
