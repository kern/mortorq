package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;

public class MastListener implements Listener {
    private static final int LEFT_MOTOR_SLOT    = 6;
    private static final int LEFT_MOTOR_CHANNEL = 3;
    
    private static final int RIGHT_MOTOR_SLOT    = 6;
    private static final int RIGHT_MOTOR_CHANNEL = 4;
    
    private static final int ENCODER_SLOT = 4;
    
    private static final int SIDE_A          = 1;
    private static final int SIDE_B          = 2;
    private static final boolean REVERSE_DIR = false;
    private static final int KP              = 1;
    private static final int KI              = 0;
    private static final int KD              = 0;
    
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
    
    Encoder encoder               = new Encoder(ENCODER_SLOT, SIDE_A, ENCODER_SLOT, SIDE_B, REVERSE_DIR);
    PIDJaguar leftMotor           = new PIDJaguar(LEFT_MOTOR_SLOT, LEFT_MOTOR_CHANNEL);
    PIDJaguar rightMotor          = new PIDJaguar(RIGHT_MOTOR_SLOT, RIGHT_MOTOR_CHANNEL);
    PIDController leftController  = new PIDController(KP, KI, KD, encoder, leftMotor);
    PIDController rightController = new PIDController(KP, KI, KD, encoder, rightMotor);
    
    public MastListener() {
        encoder.setDistancePerPulse(PULSE_DISTANCE);
        encoder.start();
        leftController.setInputRange(MIN_DISTANCE, MAX_DISTANCE);
        rightController.setInputRange(MIN_DISTANCE, MAX_DISTANCE);
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("mastCenterTop")) {
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
        } else if (name.equals("mastFeed")) {
            feed();
        } else if (name.equals("mastGround")) {
            ground();
        } else if (name.equals("mastRaise")) {
            raise();
        } else if (name.equals("mastLower")) {
            lower();
        } else if (name.equals("mastStop")) {
            stop();
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public void centerTop() {
        enablePID();
        setSetpoint(CENTER_TOP);
    }
    
    public void centerCenter() {
        enablePID();
        setSetpoint(CENTER_CENTER);
    }
    
    public void centerBottom() {
        enablePID();
        setSetpoint(CENTER_BOTTOM);
    }
    
    public void sideTop() {
        enablePID();
        setSetpoint(SIDE_TOP);
    }
    
    public void sideCenter() {
        enablePID();
        setSetpoint(SIDE_CENTER);
    }
    
    public void sideBottom() {
        enablePID();
        setSetpoint(SIDE_BOTTOM);
    }
    
    public void feed() {
        enablePID();
        setSetpoint(FEED);
    }
    
    public void ground() {
        enablePID();
        setSetpoint(GROUND);
    }
    
    public void raise() {
        disablePID();
        set(RAISE_SPEED);
    }
    
    public void lower() {
        disablePID();
        set(LOWER_SPEED);
    }
    
    public void stop() {
        disablePID();
        set(0.0);
    }
    
    private void set(double value) {
        leftMotor.set(value);
        rightMotor.set(value);
    }
    
    private void disablePID() {
        leftController.disable();
        rightController.disable();
    }
    
    private void enablePID() {
        leftController.enable();
        rightController.enable();
    }
    
    private void setSetpoint(double setpoint) {
        leftController.setSetpoint(GROUND);
        rightController.setSetpoint(GROUND);
    }
}
