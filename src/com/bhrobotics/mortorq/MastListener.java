package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.PIDController;

public class MastListener implements Listener {
    private static final int MOTOR_SLOT    = 6;
    private static final int MOTOR_CHANNEL = 4;
    private static final int ENCODER_SLOT  = 4;
    
    private static final int SIDE_A          = 7;
    private static final int SIDE_B          = 8;
    private static final boolean REVERSE_DIR = false;
    private static final int KP              = 1;
    private static final int KI              = 1;
    private static final int KD              = 1;
    
    private static final double PULSE_DISTANCE = 1.0;
    private static final double MAX_DISTANCE   = 1000;
    private static final double MIN_DISTANCE   = 0;
    
    private static final double CENTER_TOP    = 0.0;
    private static final double CENTER_CENTER = 0.0;
    private static final double CENTER_BOTTOM = 0.0;
    private static final double SIDE_TOP      = 0.0;
    private static final double SIDE_CENTER   = 0.0;
    private static final double SIDE_BOTTOM   = 0.0;
    private static final double FEED          = 0;
    private static final double GROUND        = 0;
    
    private static final double RAISE_SPEED = 0.1;
    private static final double LOWER_SPEED = -0.1;
    
    DistanceEncoder encoder  = new DistanceEncoder(ENCODER_SLOT, SIDE_A, ENCODER_SLOT, SIDE_B, REVERSE_DIR);
    PIDJaguar motor          = new PIDJaguar(MOTOR_SLOT, MOTOR_CHANNEL);
    PIDController controller = new PIDController(KP, KI, KD, encoder, motor);
    
    public MastListener() {
        encoder.setDistancePerPulse(PULSE_DISTANCE);
        encoder.start();
        controller.setInputRange(MIN_DISTANCE, MAX_DISTANCE);
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name == "mastCenterTop") {
            centerTop();
        } else if (name == "mastCenterCenter") {
            centerCenter();
        } else if (name == "mastCenterBottom") {
            centerBottom();
        } else if (name == "mastSideTop") {
            sideTop();
        } else if (name == "mastSideCenter") {
            sideCenter();
        } else if (name == "mastSideBottom") {
            sideBottom();
        } else if (name == "mastFeed") {
            feed();
        } else if (name == "mastGround") {
            ground();
        } else if (name == "mastRaise") {
            raise();
        } else if (name == "mastLower") {
            lower();
        } else if (name == "mastStop") {
            stop();
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public void centerTop() {
        controller.enable();
        controller.setSetpoint(CENTER_TOP);
    }
    
    public void centerCenter() {
        controller.enable();
        controller.setSetpoint(CENTER_CENTER);
    }
    
    public void centerBottom() {
        controller.enable();
        controller.setSetpoint(CENTER_BOTTOM);
    }
    
    public void sideTop() {
        controller.enable();
        controller.setSetpoint(SIDE_TOP);
    }
    
    public void sideCenter() {
        controller.enable();
        controller.setSetpoint(SIDE_CENTER);
    }
    
    public void sideBottom() {
        controller.enable();
        controller.setSetpoint(SIDE_BOTTOM);
    }
    
    public void feed() {
        controller.enable();
        controller.setSetpoint(FEED);
    }
    
    public void ground() {
        controller.enable();
        controller.setSetpoint(GROUND);
    }
    
    public void raise() {
        controller.disable();
        motor.set(RAISE_SPEED);
    }
    
    public void lower() {
        controller.disable();
        motor.set(LOWER_SPEED);
    }
    
    public void stop() {
        controller.disable();
        motor.set(0.0);
    }
}