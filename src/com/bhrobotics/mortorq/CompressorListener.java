package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class CompressorListener implements Listener {
    private static final int PRESSURE_SLOT    = 6;
    private static final int PRESSURE_CHANNEL = 14;
    
    private static final int RELAY_SLOT    = 4;
    private static final int RELAY_CHANNEL = 1;
    
    private DigitalInput pressure = new DigitalInput(PRESSURE_SLOT, PRESSURE_CHANNEL);
    private Relay relay = new Relay(RELAY_SLOT, RELAY_CHANNEL, Relay.Direction.kForward);
    
    private String mode;
    private boolean state;
    
    public CompressorListener() {
        stop();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("tick")) {
            update();
        } else if (name.equals("compressorAuto")) {
            auto();
        } else if (name.equals("compressorManual")) {
            manual();
        } else if (name.equals("compressorManualOn")) {
            state = true;
        } else if (name.equals("compressorManualOff")) {
            state = false;
        } else if (name.equals("compressorStop")) {
            stop();
        }
    }
    
    public void auto() {
        mode = "auto";
    }
    
    public void manual() {
        mode = "manual";
    }
    
    public void stop() {
        mode = "manual";
        state = false;
    }
    
    private void update() {
        Relay.Value value = Relay.Value.kOff;
        
        if ((mode.equals("auto") && !pressure.get()) || (mode.equals("manual") && state)) {
            value = Relay.Value.kOn;
        }
        
        relay.set(value);
    }
}