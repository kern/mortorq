package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.Solenoid;

public class Wrist {
    private static final int SLOT    = 8;
    private static final int CHANNEL = 2;
    
    private static final boolean RAISED  = true;
    private static final boolean LOWERED = false;
    
    Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    private static Wrist instance = new Wrist();
    
    private Wrist() {}
    
    public boolean get() {
        return solenoid.get();
    }
    
    public void set(boolean value) {
        solenoid.set(value);
    }
    
    public void toggle() {
        set(!get());
    }
    
    public void raise() {
        set(RAISED);
    }
    
    public void lower() {
        set(LOWERED);
    }
    
    public static Wrist getInstance() {
        return instance;
    }
}