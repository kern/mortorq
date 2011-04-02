package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

public class Compressor {
    private static final int PRESSURE_SLOT    = 6;
    private static final int PRESSURE_CHANNEL = 14;
    
    private static final int RELAY_SLOT    = 4;
    private static final int RELAY_CHANNEL = 1;
    
    private DigitalInput pressure = new DigitalInput(PRESSURE_SLOT, PRESSURE_CHANNEL);
    private Relay relay = new Relay(RELAY_SLOT, RELAY_CHANNEL, Relay.Direction.kForward);
    private String mode = "manual";
    private boolean manualState = false;
    
    private static Compressor instance = new Compressor();
    
    private Compressor() {}
    
    public void auto() {
        mode = "auto";
    }
    
    public void manual() {
        mode = "manual";
    }
    
    public void stop() {
        mode = "manual";
        manualState = false;
    }
    
    public void setManualState(boolean s) {
        manualState = s;
    }
    
    public void update() {
        Relay.Value value = Relay.Value.kOff;
        
        if ((mode.equals("auto") && !pressure.get()) || (mode.equals("manual") && manualState)) {
            value = Relay.Value.kOn;
        }
        
        relay.set(value);
    }
    
    public static Compressor getInstance() {
        return instance;
    }
}