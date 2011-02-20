package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;

public class MastListener implements Listener {
    private static final int MOTOR_SLOT          = 4;
    private static final int LEFT_MOTOR_CHANNEL  = 3;
    private static final int RIGHT_MOTOR_CHANNEL = 4;
    
    private static final int LIMIT_SWITCH_SLOT    = 4;
    private static final int LIMIT_SWITCH_CHANNEL = 1;
    
    private static final int ENCODER_SLOT = 6;
    private static final int SIDE_A       = 1;
    private static final int SIDE_B       = 2;
    
    private static final double TOP_LIMIT    = 1550.0;
    private static final double BOTTOM_LIMIT = 0.0;
    
    private static final double CENTER_TOP    = 1395.0;
    private static final double CENTER_CENTER = 729.0;
    private static final double CENTER_BOTTOM = 0.0;
    private static final double SIDE_TOP      = 1264.0;
    private static final double SIDE_CENTER   = 588.0;
    private static final double SIDE_BOTTOM   = 285.0;
    private static final double FEED          = 400.0;
    private static final double GROUND        = 0.0;
    private static final double NONE          = -1;
    
    private static final double UP_SPEED   = -1.0;
    private static final double DOWN_SPEED = 0.2;
    
    private static final double DELTA = 10.0;
    
    DigitalInput limitSwitch = new DigitalInput(LIMIT_SWITCH_SLOT, LIMIT_SWITCH_CHANNEL);
    
    Encoder encoder;
    
    Victor leftMotor  = new Victor(MOTOR_SLOT, LEFT_MOTOR_CHANNEL);
    Victor rightMotor = new Victor(MOTOR_SLOT, RIGHT_MOTOR_CHANNEL);
    
    private boolean calibrated      = false;
    private boolean encoderOverride = false;
    private double setpoint         = NONE;
    
    public MastListener() {
        DigitalInput sideA = new DigitalInput(ENCODER_SLOT, SIDE_A);
        DigitalInput sideB = new DigitalInput(ENCODER_SLOT, SIDE_B);
        
        encoder = new Encoder(sideA, sideB, false, Encoder.EncodingType.k4X);
        encoder = new Encoder(sideA, sideB, false, Encoder.EncodingType.k4X);
        encoder.start();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("tick")) {
            if (!calibrated && getLimitSwitch()) {
                calibrated = true;
                encoder.reset();
            }
            
            if (isMovingDown() && getLimitSwitch()) {
                stop();
                encoder.reset();
            }
            
            if (isMovingUp() && encoder.getDistance() >= TOP_LIMIT) {
                stop();
            }
            
            if (setpoint != NONE && calibrated) {
                double error = Math.abs(encoder.getDistance() - setpoint);
                
                if (error > DELTA) {
                    if (encoder.getDistance() < setpoint) {
                        set(UP_SPEED);
                    } else {
                        set(DOWN_SPEED);
                    }
                } else {
                    set(0.0);
                }
            }
        } else if (name.equals("mastGround")) {
            ground();
        } else if (name.equals("mastFeed")) {
            feed();
        } else if (name.equals("mastNone")) {
            none();
        } else if (name.equals("mastCenterTop")) {
            centerTop();
        } else if (name.equals("mastCenterCenter")) {
            centerCenter();
        } else if (name.equals("mastCenterBottom")) {
            centerBottom();
        } else if (name.equals("mastSideTop")) {
            sideTop();
        } else if (name.equals("mastSideCenter")) {
            sideCenter();
        } else if (name.equals("mastSideBottom")) {
            sideBottom();
        } else if (name.equals("mastRelativeUp")) {
            relativeUp();
        } else if (name.equals("mastRelativeDown")) {
            relativeDown();
        } else if (name.equals("mastRelativeStop")) {
            stop();
        } else if (name.equals("mastStop")) {
            stop();
        } else if (name.equals("mastEncoderOverrideOn")) {
            encoderOverride = true;
        } else if (name.equals("mastEncoderOverrideOff")) {
            encoderOverride = false;
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public boolean isMovingUp() {
        return get() < 0.0;
    }
    
    public boolean isStopped() {
        return get() == 0.0;
    }
    
    public boolean isMovingDown() {
        return get() > 0.0;
    }
    
    public double get() {
        return leftMotor.get();
    }
    
    public void set(double value) {
        if (value > 0.0 && getLimitSwitch()) {
            stop();
        } else if (value < 0.0 && encoder.getDistance() >= TOP_LIMIT) {
            stop();
        } else {
            leftMotor.set(value);
            rightMotor.set(value);
        }
    }
    
    public void relativeUp() {
        none();
        
        if (calibrated || encoderOverride) {
            set(UP_SPEED);
        }
    }
    
    public void relativeDown() {
        none();
        set(DOWN_SPEED);
    }
    
    public void stop() {
        none();
        set(0.0);
    }
    
    public void centerTop() {
        setpoint = CENTER_TOP;
    }
    
    public void centerCenter() {
        setpoint = CENTER_CENTER;
    }
    
    public void centerBottom() {
        setpoint = CENTER_BOTTOM;
    }
    
    public void sideTop() {
        setpoint = SIDE_TOP;
    }
    
    public void sideCenter() {
        setpoint = SIDE_CENTER;
    }
    
    public void sideBottom() {
        setpoint = SIDE_BOTTOM;
    }
    
    public void feed() {
        setpoint = FEED;
    }
    
    public void ground() {
        setpoint = GROUND;
    }
    
    public void none() {
        setpoint = NONE;
    }
    
    private boolean getLimitSwitch() {
        return !limitSwitch.get();
    }
}
