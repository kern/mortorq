package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Filter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.Hashtable;

public class LineTrackerFilter extends Filter {
    private static final int SENSOR_SLOT      = 6;
    private static final int SENSOR_L_CHANNEL = 1;
    private static final int SENSOR_C_CHANNEL = 2;
    private static final int SENSOR_R_CHANNEL = 3;
    
    private static final int POWER_SLOT     = 8;
    private static final int SENSOR_L_POWER = 5;
    private static final int SENSOR_C_POWER = 6;
    private static final int SENSOR_R_POWER = 7;
    
    private static final double FORWARD_X        = 1;
    private static final double FORWARD_Y        = 1;
    private static final double FORWARD_ROTATION = 1;
    
    private static final double RIGHT_X        = 1;
    private static final double RIGHT_Y        = 1;
    private static final double RIGHT_ROTATION = 1;
    
    private static final double LEFT_X        = 1;
    private static final double LEFT_Y        = 1;
    private static final double LEFT_ROTATION = 1;
    
    private Hashtable fowardData = new Hashtable();
    private Hashtable rightData  = new Hashtable();
    private Hashtable leftData   = new Hashtable();
     
    private DigitalInput sensorL = new DigitalInput(SENSOR_SLOT, SENSOR_L_CHANNEL); 
    private DigitalInput sensorC = new DigitalInput(SENSOR_SLOT, SENSOR_C_CHANNEL); 
    private DigitalInput sensorR = new DigitalInput(SENSOR_SLOT, SENSOR_R_CHANNEL);
    
    private Solenoid sensorLPower = new Solenoid(POWER_SLOT, SENSOR_L_POWER);
    private Solenoid sensorCPower = new Solenoid(POWER_SLOT, SENSOR_C_POWER);
    private Solenoid sensorRPower = new Solenoid(POWER_SLOT, SENSOR_R_POWER);
    
    private EventEmitter emitter = new EventEmitter();
    
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
    }
    
    public void handle(Event event) {
        if (sensorL.get() && sensorC.get() && sensorR.get()) {
            stop();
        } else if (sensorL.get()) {
            right();
        } else if (sensorC.get()) {
            foward();
        } else if (sensorR.get()) {
            left();
        } else {
            stop();
        }
    }
    
    public void bound(EventEmitter emitter, String name) {}
    public void unbound(EventEmitter emitter, String name) {}
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void left() {
        trigger("drive", leftData);
    }
    
    public void foward() {
        trigger("drive", fowardData);
    }
    
    public void right() {
        trigger("drive", rightData);
    }
    
    public void stop() {
        trigger("stopMotors");
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