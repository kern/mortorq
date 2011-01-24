package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.Joystick;
import java.util.Hashtable;

public class JoystickListener extends Listener {
    private Joystick joystick1 = new Joystick(1);
    private Joystick joystick2 = new Joystick(2);
    private Joystick joystick3 = new Joystick(3);
    private Joystick joystick4 = new Joystick(4);
    
    private EventEmitter emitter = new EventEmitter();
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void handle(Event event) {
        emitter.trigger("updateJoysticks");
        
        Hashtable joystick1Data = new Hashtable();
        joystick1Data.put("joystick", joystick1);
        emitter.trigger("updateJoystick1", joystick1Data);
        
        Hashtable joystick2Data = new Hashtable();
        joystick2Data.put("joystick", joystick2);
        emitter.trigger("updateJoystick2", joystick2Data);
        
        Hashtable joystick3Data = new Hashtable();
        joystick3Data.put("joystick", joystick3);
        emitter.trigger("updateJoystick3", joystick3Data);
        
        Hashtable joystick4Data = new Hashtable();
        joystick4Data.put("joystick", joystick4);
        emitter.trigger("updateJoystick4", joystick4Data);
    }
}