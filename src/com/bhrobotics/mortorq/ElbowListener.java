package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.Solenoid;

public class ElbowListener extends Listener {
    private static final int SLOT = 2;
    private static final int CHANNEL = 2;
    private Solenoid solenoid = new Solenoid(SLOT, CHANNEL);
    
    public void lower() {
        solenoid.set(false);
    }
    
    public void raise() {
        solenoid.set(true);    
    }
    
    public void handle(Event event) {
        lower();
    }
} 