package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import java.util.Hashtable;

public class LineTrackerListener implements Listener {
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
    
    private DigitalInput sensorL = new DigitalInput(SENSOR_SLOT, SENSOR_L_CHANNEL); 
    private DigitalInput sensorC = new DigitalInput(SENSOR_SLOT, SENSOR_C_CHANNEL); 
    private DigitalInput sensorR = new DigitalInput(SENSOR_SLOT, SENSOR_R_CHANNEL);
    
    private Solenoid sensorLPower = new Solenoid(POWER_SLOT, SENSOR_L_POWER);
    private Solenoid sensorCPower = new Solenoid(POWER_SLOT, SENSOR_C_POWER);
    private Solenoid sensorRPower = new Solenoid(POWER_SLOT, SENSOR_R_POWER);
    
    private EventEmitter emitter = new EventEmitter();
    private boolean atPeg = false;
    
    public LineTrackerListener() {
        sensorLPower.set(true);
        sensorCPower.set(true);
        sensorRPower.set(true);
    }
    
    public void handle(Event event) {
        if (!atPeg) {
            Claw.getInstance().wide();
            Mast.getInstance().centerTop();
            
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
            } else {
                stop();
            }
        }
    }
    
    public void left() {
        System.out.println("[auto] Left.");
        MecanumDrive.getInstance().drive(LEFT_X, LEFT_Y, LEFT_ROTATION);
    }
    
    public void forward() {
        System.out.println("[auto] Forward.");
        MecanumDrive.getInstance().drive(FORWARD_X, FORWARD_Y, FORWARD_ROTATION);
    }
    
    public void right() {
        System.out.println("[auto] Right.");
        MecanumDrive.getInstance().drive(RIGHT_X, RIGHT_Y, RIGHT_ROTATION);
    }
    
    public void backward() {
        System.out.println("[auto] Backward.");
        MecanumDrive.getInstance().drive(BACKWARD_X, BACKWARD_Y, BACKWARD_ROTATION);
    }
    
    public void stop() {
        System.out.println("[auto] Stopping.");
        MecanumDrive.getInstance().stop();
    }
    
    public void setAtPeg(boolean p) {
        atPeg = p;
    }
    
    private void pegReached() {
        atPeg = true;
        stop();
        
        emitter.bind("clawNarrow", new Listener() {
            public void handle(Event event) {
                Claw.getInstance().narrow();
            }
        });
        
        emitter.bind("backward", new Listener() {
            public void handle(Event event) {
                backward();
            }
        });
        
        emitter.bind("mastGround", new Listener() {
            public void handle(Event event) {
                Mast.getInstance().ground();
            }
        });
        
        emitter.bind("stop", new Listener() {
            public void handle(Event event) {
                stop();
            }
        });
        
        emitter.schedule("clawNarrow", PAUSE_DELAY);
        emitter.schedule("backward", PAUSE_DELAY + CLAW_NARROW_DELAY);
        emitter.schedule("mastGround", PAUSE_DELAY + CLAW_NARROW_DELAY + MAST_GROUND_DELAY);
        emitter.schedule("stop", PAUSE_DELAY + CLAW_NARROW_DELAY + BACKWARD_DELAY);
    }
}