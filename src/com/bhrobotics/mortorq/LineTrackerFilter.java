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
    private static final double FORWARD_Y        = -0.2;
    private static final double FORWARD_ROTATION = 0.0;
    
    private static final double RIGHT_X        = 0.0;
    private static final double RIGHT_Y        = -0.2;
    private static final double RIGHT_ROTATION = 0.2;
    
    private static final double LEFT_X        = 0.0;
    private static final double LEFT_Y        = -0.2;
    private static final double LEFT_ROTATION = -0.2;
    
    private static final double STRAFE_X        = -0.7;
    private static final double STRAFE_Y        = 0.0;
    private static final double STRAFE_ROTATION = 0.0;
    
    private Hashtable fowardData = new Hashtable();
    private Hashtable rightData  = new Hashtable();
    private Hashtable leftData   = new Hashtable();
    private Hashtable strafeData = new Hashtable();
     
    private DigitalInput sensorL = new DigitalInput(SENSOR_SLOT, SENSOR_L_CHANNEL); 
    private DigitalInput sensorC = new DigitalInput(SENSOR_SLOT, SENSOR_C_CHANNEL); 
    private DigitalInput sensorR = new DigitalInput(SENSOR_SLOT, SENSOR_R_CHANNEL);
    
    private Solenoid sensorLPower = new Solenoid(POWER_SLOT, SENSOR_L_POWER);
    private Solenoid sensorCPower = new Solenoid(POWER_SLOT, SENSOR_C_POWER);
    private Solenoid sensorRPower = new Solenoid(POWER_SLOT, SENSOR_R_POWER);
    
    private EventEmitter emitter = new EventEmitter();
    
    private TimeoutEmitter strafeTimeout = new TimeoutEmitter();
    private int strafeState = 0;
    
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
        
        strafeData.put("x", new Double(STRAFE_X));
        strafeData.put("y", new Double(STRAFE_Y));
        strafeData.put("rotation", new Double(STRAFE_ROTATION));
        
        strafeTimeout.bind("all", this);
    }
    
    public void handle(Event event) {
        if (event.getName().equals("stopStrafe")) {
            strafeState = 2;
            forward();
        } else {
            if (strafeState == 2 && (sensorL.get() || sensorR.get() || sensorC.get())) {
                strafeState = 3;
                stop();
            } else if (sensorL.get() && strafeState == 0) {
                right();
            } else if (sensorR.get() && strafeState == 0) {
                left();
            } else if (sensorC.get() && strafeState == 0) {
                forward();
            } else if (strafeState == 0) {
                strafeState = 1;
                strafeTimeout.schedule("stopStrafe", 2.0);
                strafe();
            }
        }
    }
    
    public void bound(EventEmitter emitter, String name) {
        strafeState = 0;
    }
    
    public void unbound(EventEmitter emitter, String name) {
        strafeState = 0;
    }
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void left() {
        trigger("drive", leftData);
    }
    
    public void forward() {
        trigger("drive", fowardData);
    }
    
    public void right() {
        trigger("drive", rightData);
    }
    
    public void strafe() {
        trigger("drive", strafeData);
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
