package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.Solenoid;

public class Minibot {
    private static final int SLOT    = 8;
    private static final int CHANNEL = 4;
    
    private static final boolean RETRACTED = false;
    private static final boolean DEPLOYED = true;
    
    private Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    private boolean endGame = false;
    private boolean interlock = false;
    
    public static Minibot instance = new Minibot();
    
    private Minibot() {}
    
    public boolean get() {
        return solenoid.get();
    }
    
    public void set(boolean value) {
        solenoid.set(value);
    }
    
    public void setEndGame(boolean e) {
        if(e) {
            endGame = true;
            
            if (isReady()) {
                deploy();
            }
        } else {
            retract();
        }
    }
    
    public void setInterlock(boolean i) {
        if(i) {
            interlock = true;
            
            if (isReady()) {
                deploy();
            }
        } else {
            retract();
        }
    }
    
    public void toggle() {
        set(!get());
    }
    
    public void deploy() {
        set(DEPLOYED);
        endGame = true;
        interlock = true;
    }
    
    public void retract() {
        set(RETRACTED);
        endGame = false;
        interlock = false;
    }
    
    public boolean isReady() {
        return endGame && interlock;
    }
    
    public static Minibot getInstance() {
        return instance;
    }
}