package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.TimeoutEmitter;
import com.bhrobotics.morlib.Filter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.Hashtable;

public class LineTrackerFilter extends Filter {
    private static final int SENSOR_SLOT      = 6;
    private static final int SENSOR_L_CHANNEL = 11;
    private static final int SENSOR_C_CHANNEL = 12;
    private static final int SENSOR_R_CHANNEL = 13;
    
    private static final int POWER_SLOT     = 8;
    private static final int SENSOR_L_POWER = 5;
    private static final int SENSOR_C_POWER = 6;
    private static final int SENSOR_R_POWER = 7;
    
    private static final double FORWARD_X        = 0.0;
    private static final double FORWARD_Y        = -0.11;
    private static final double FORWARD_ROTATION = 0.0;
    
    private static final double RIGHT_X        = 0.0;
    private static final double RIGHT_Y        = -0.1;
    private static final double RIGHT_ROTATION = 0.25;
    
    private static final double LEFT_X        = 0.0;
    private static final double LEFT_Y        = -0.1;
    private static final double LEFT_ROTATION = -0.2;
    
    private static final double BACKWARD_X        = 0.0;
    private static final double BACKWARD_Y        = 0.25;
    private static final double BACKWARD_ROTATION = 0.0;
    
    private static final double PAUSE_DELAY = 1.0;
    private static final double CLAW_NARROW_DELAY = 0.5;
    private static final double MAST_GROUND_DELAY = 0.5;
    private static final double BACKWARD_DELAY = 2.0;
    
    private Hashtable fowardData   = new Hashtable();
    private Hashtable rightData    = new Hashtable();
    private Hashtable leftData     = new Hashtable();
    private Hashtable backwardData = new Hashtable();
     
    private DigitalInput sensorL = new DigitalInput(SENSOR_SLOT, SENSOR_L_CHANNEL); 
    private DigitalInput sensorC = new DigitalInput(SENSOR_SLOT, SENSOR_C_CHANNEL); 
    private DigitalInput sensorR = new DigitalInput(SENSOR_SLOT, SENSOR_R_CHANNEL);
    
    private Solenoid sensorLPower = new Solenoid(POWER_SLOT, SENSOR_L_POWER);
    private Solenoid sensorCPower = new Solenoid(POWER_SLOT, SENSOR_C_POWER);
    private Solenoid sensorRPower = new Solenoid(POWER_SLOT, SENSOR_R_POWER);
    
    private EventEmitter emitter = new EventEmitter();
    private TimeoutEmitter timeout = new TimeoutEmitter();
    
    private boolean atPeg = false;
    
    public LineTrackerFilter() {
        powerOn();
        
        fowardData.put("x", new Double(FORWARD_X));
        fowardData.put("y", new Double(FORWARD_Y));
        fowardData.put("rotation", new Double(FORWARD_ROTATION));
        
        rightData.put("x", new Double(RIGHT_X));
        rightData.put("y", new Double(RIGHT_Y));
        rightData.put("rotation", new Double(RIGHT_ROTATION));
        
        leftData.put("x", new Double(LEFT_X));
        leftData.put("y", new Double(LEFT_Y));
        leftData.put("rotation", new Double(LEFT_ROTATION));
        
        backwardData.put("x", new Double(BACKWARD_X));
        backwardData.put("y", new Double(BACKWARD_Y));
        backwardData.put("rotation", new Double(BACKWARD_ROTATION));
        
        timeout.bind("all", this);
    }
    
    public void handle(Event event) {
        if (event.getName().equals("clawNarrow")) {
            Claw.getInstance().narrow();
        } else if (event.getName().equals("backward")) {
            backward();
        } else if (event.getName().equals("mastGround")) {
            trigger("mastGround");
        } else if (event.getName().equals("stop")) {
            stop();
        } else {
            if (!atPeg) {
                Claw.getInstance().wide();
                
                boolean l = sensorL.get();
                boolean c = sensorC.get();
                boolean r = sensorR.get();
                
                if (l && r && c) {
                    pegReached();
                } else if (l) {
                    right();
                } else if (r) {
                    left();
                } else if (c) {
                    forward();
                    trigger("mastCenterTop");
                } else {
                    stop();
                }
            }
        }
    }
    
    public void bound(EventEmitter emitter, String name) {
        atPeg = false;
        stop();
        Claw.getInstance().wide();
        trigger("mastCenterTop");
    }
    
    public void unbound(EventEmitter emitter, String name) {}
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void left() {
        trigger("drive", leftData);
        System.out.println("[auto] Left.");
    }
    
    public void forward() {
        trigger("drive", fowardData);
        System.out.println("[auto] Forward.");
    }
    
    public void right() {
        trigger("drive", rightData);
        System.out.println("[auto] Right.");
    }
    
    public void backward() {
        trigger("drive", backwardData);
        System.out.println("[auto] Backward.");
    }
    
    public void stop() {
        trigger("stopMotors");
        System.out.println("[auto] Stopping.");
    }
    
    private void pegReached() {
        atPeg = true;
        stop();
        timeout.schedule("clawNarrow", PAUSE_DELAY);
        timeout.schedule("backward", PAUSE_DELAY + CLAW_NARROW_DELAY);
        timeout.schedule("mastGround", PAUSE_DELAY + CLAW_NARROW_DELAY + MAST_GROUND_DELAY);
        timeout.schedule("stop", PAUSE_DELAY + CLAW_NARROW_DELAY + BACKWARD_DELAY);
    }
    
    public void powerOn() {
        sensorLPower.set(true);
        sensorCPower.set(true);
        sensorRPower.set(true);
    }
    
    public void powerOff() {
        sensorLPower.set(false);
        sensorCPower.set(false);
        sensorRPower.set(false);
    }
}
