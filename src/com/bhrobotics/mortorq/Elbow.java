package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.Solenoid;

public class Elbow {
    private static final int SLOT    = 8;
    private static final int CHANNEL = 1;
    
    private static final boolean RAISED  = false;
    private static final boolean LOWERED = true;
    
    private Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    private static Elbow instance = new Elbow();
    
    private Elbow() {}
    
    public boolean get() {
        return solenoid.get();
    }
    
    public void set(boolean value) {
        solenoid.set(value);
    }
    
    public void toggle() {
        set(!get());
    }
    
    public void lower() {
        set(LOWERED);
    }
    
    public void raise() {
        set(RAISED);
    }
    
    public static Elbow getInstance() {
        return instance;
    }
}