package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.Joystick;
import java.util.Hashtable;

// TODO: Test me.
public class JoystickFilter extends Filter {
    private EventEmitter emitter = new EventEmitter();
    private Joystick joystick1 = new Joystick(1);
    private Joystick joystick2 = new Joystick(2);
    private Joystick joystick3 = new Joystick(3);
    private Joystick joystick4 = new Joystick(4);
    
    public void handle(Event event) {
        trigger("updateJoysticks");
        
        Hashtable joystick1Data = new Hashtable();
        joystick1Data.put("joystick", joystick1);
        trigger("updateJoystick1", joystick1Data);
        
        Hashtable joystick2Data = new Hashtable();
        joystick2Data.put("joystick", joystick2);
        trigger("updateJoystick2", joystick2Data);
        
        Hashtable joystick3Data = new Hashtable();
        joystick3Data.put("joystick", joystick3);
        trigger("updateJoystick3", joystick3Data);
        
        Hashtable joystick4Data = new Hashtable();
        joystick4Data.put("joystick", joystick4);
        trigger("updateJoystick4", joystick4Data);
    }
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
}