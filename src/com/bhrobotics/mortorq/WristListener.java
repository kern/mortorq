package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.Solenoid;

public class WristListener extends Listener{
    private static final int SLOT = 1;
    private static final int CHANNEL = 1;
    Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public WristListener() {
        reset();
    }
    
    public void handle(Event event) {
        solenoid.set(!solenoid.get());
    }
    
    public void reset() {
        solenoid.set(false);
    }
}