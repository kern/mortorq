package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.Solenoid;

public class Claw {
    private static final int SLOT    = 8;
    private static final int CHANNEL = 3;
    
    private static final boolean WIDE   = false;
    private static final boolean NARROW = true;
    
    Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    private static Claw instance = new Claw();
    
    private Claw() {}
    
    public boolean get() {
        return solenoid.get();
    }
    
    public void set(boolean value) {
        solenoid.set(value);
    }
    
    public void toggle() {
        set(!get());
    }
    
    public void narrow() {
        set(NARROW);
    }
    
    public void wide() {
        set(WIDE);
    }
    
    public static Claw getInstance() {
        return instance;
    }
}