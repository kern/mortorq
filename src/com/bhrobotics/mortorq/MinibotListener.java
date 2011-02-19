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
    
    private boolean endGame   = false;
    private boolean interlock = false;
    
    public MinibotListener() {
        reset();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("startEndGame")) {
            endGame = true;
            
            if (isReady()) {
                deploy();
            }
        } else if (name.equals("minibotInterlockOn")) {
            interlock = true;
            
            if (isReady()) {
                deploy();
            }
        } else if (name.equals("minibotInterlockOff")) {
            interlock = false;
        } else if (name.equals("minibotDeploy")) {
            deploy();
        } else if (name.equals("minibotRedact")) {
            redact();
        } else if (name.equals("minibotReset")) {
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
        interlock = false;
    }
    
    public void deploy() {
        set(DEPLOYED);
    }
    
    public void redact() {
        set(REDACTED);
    }
    
    public boolean isReady() {
        return endGame && interlock;
    }
}