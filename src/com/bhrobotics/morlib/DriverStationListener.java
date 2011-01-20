package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.Joystick;
import java.util.Hashtable;

public class DriverStationListener extends Listener {
    private EventEmitter emitter = new EventEmitter();
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void handle(Event event) {
        // TODO: Handle changed IO data.
    }
}