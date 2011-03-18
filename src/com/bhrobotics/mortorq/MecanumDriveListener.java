package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

class MecanumDriveListener implements Listener {
    private static final int MOTOR_SLOT   = 4;
    private static final int ENCODER_SLOT = 6;
    
    private static final double SCALE_FAST = 1.0;
    private static final double SCALE_SLOW = 0.5;
    
    private static final double MAX_DEADBAND = 0.3;
    private static final double MIN_DEADBAND = -0.3;
    
    private static final int RIGHT_FRONT_MOTOR    = 2;
    private static final int RIGHT_FRONT_SIDE_A   = 5;
    private static final int RIGHT_FRONT_SIDE_B   = 6;
    private static final double RIGHT_FRONT_SCALE = -1.0;
    private static final double RIGHT_FRONT_MAX   = 1.0;
    private static final double RIGHT_FRONT_MIN   = 0.0;
    
    private static final int RIGHT_BACK_MOTOR    = 6;
    private static final int RIGHT_BACK_SIDE_A   = 9;
    private static final int RIGHT_BACK_SIDE_B   = 10;
    private static final double RIGHT_BACK_SCALE = -1.0;
    private static final double RIGHT_BACK_MAX   = 1.0;
    private static final double RIGHT_BACK_MIN   = 0.0;
    
    private static final int LEFT_FRONT_MOTOR    = 1;
    private static final int LEFT_FRONT_SIDE_A   = 3;
    private static final int LEFT_FRONT_SIDE_B   = 4;
    private static final double LEFT_FRONT_SCALE = 1.0;
    private static final double LEFT_FRONT_MAX   = 1.0;
    private static final double LEFT_FRONT_MIN   = 0.0;
    
    private static final int LEFT_BACK_MOTOR    = 5;
    private static final int LEFT_BACK_SIDE_A   = 7;
    private static final int LEFT_BACK_SIDE_B   = 8;
    private static final double LEFT_BACK_SCALE = 1.0;
    private static final double LEFT_BACK_MAX   = 1.0;
    private static final double LEFT_BACK_MIN   = 0.0;
    
    private static final int LIMIT_SWITCH_SLOT    = 4;
    private static final int LIMIT_SWITCH_CHANNEL = 1;
    
    Encoder rightFrontEncoder;
    Encoder rightBackEncoder;
    Encoder leftFrontEncoder;
    Encoder leftBackEncoder;
    
    Victor rightFrontMotor = new Victor(MOTOR_SLOT, RIGHT_FRONT_MOTOR);
    Victor rightBackMotor  = new Victor(MOTOR_SLOT, RIGHT_BACK_MOTOR);
    Victor leftFrontMotor  = new Victor(MOTOR_SLOT, LEFT_FRONT_MOTOR);
    Victor leftBackMotor   = new Victor(MOTOR_SLOT, LEFT_BACK_MOTOR);
    
    public MecanumDriveListener() {
        DigitalInput rightFrontSideA = new DigitalInput(ENCODER_SLOT, RIGHT_FRONT_SIDE_A);
        DigitalInput rightFrontSideB = new DigitalInput(ENCODER_SLOT, RIGHT_FRONT_SIDE_B);
        
        DigitalInput rightBackSideA = new DigitalInput(ENCODER_SLOT, RIGHT_BACK_SIDE_A);
        DigitalInput rightBackSideB = new DigitalInput(ENCODER_SLOT, RIGHT_BACK_SIDE_B);
        
        DigitalInput leftFrontSideA = new DigitalInput(ENCODER_SLOT, LEFT_FRONT_SIDE_A);
        DigitalInput leftFrontSideB = new DigitalInput(ENCODER_SLOT, LEFT_FRONT_SIDE_B);
        
        DigitalInput leftBackSideA = new DigitalInput(ENCODER_SLOT, LEFT_BACK_SIDE_A);
        DigitalInput leftBackSideB = new DigitalInput(ENCODER_SLOT, LEFT_BACK_SIDE_B);
        
        // I have extreme hatred toward whoever made this code necessary. I will
        // hunt you down with a large pack of rabid dogs (and possibly squirrels)
        // and gnaw off your face. Thank you, that is all.
        rightFrontEncoder = new Encoder(rightFrontSideA, rightFrontSideB, true, Encoder.EncodingType.k1X);
        rightBackEncoder  = new Encoder(rightBackSideA, rightBackSideB, false, Encoder.EncodingType.k1X);
        rightBackEncoder  = new Encoder(rightBackSideA, rightBackSideB, false, Encoder.EncodingType.k1X);
        leftFrontEncoder  = new Encoder(leftFrontSideA, leftFrontSideB, false, Encoder.EncodingType.k1X);
        leftFrontEncoder  = new Encoder(leftFrontSideA, leftFrontSideB, false, Encoder.EncodingType.k1X);
        leftBackEncoder   = new Encoder(leftBackSideA, leftBackSideB, false, Encoder.EncodingType.k1X);
        leftBackEncoder   = new Encoder(leftBackSideA, leftBackSideB, false, Encoder.EncodingType.k1X);
        leftBackEncoder   = new Encoder(leftBackSideA, leftBackSideB, false, Encoder.EncodingType.k1X);
        
        stop();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.startsWith("changeJoystick")) {
            Joystick joystick = (Joystick) event.getData("joystick");
            
            double x = deadband(joystick.getX());
            double y = deadband(joystick.getY());
            double z = deadband(joystick.getZ());
            
            if (joystick.getTrigger() || getLimitSwitch()) {
                drive(x, y, z, SCALE_SLOW);
            } else {
                drive(x, y, z, SCALE_FAST);
            }
        } else if (name.equals("drive")) {
            double x = ((Double) event.getData("x")).doubleValue();
            double y = ((Double) event.getData("y")).doubleValue();
            double rotation = ((Double) event.getData("rotation")).doubleValue();
            
            drive(x, y, rotation);
        } else if (name.startsWith("changeMotor")) {
            double setpoint = ((Double) event.getData("value")).doubleValue();
            
            if (name.equals("changeMotorRightFront")) {
                setRightFront(setpoint);
            } else if (name.equals("changeMotorRightBack")) {
                setRightBack(setpoint);
            } else if (name.equals("changeMotorLeftFront")) {
                setLeftFront(setpoint);
            } else if (name.equals("changeMotorLeftBack")) {
                setLeftBack(setpoint);
            }
        } else if (name.equals("stopMotors")) {
            stop();
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public void drive(double x, double y, double rotation) {
        drive(x, y, rotation, SCALE_FAST);
    }
    
    public void drive(double x, double y, double rotation, double scale) {
        setRightFront((y + x + rotation) * scale);
        setRightBack((y - x + rotation) * scale);
        setLeftFront((y - x - rotation) * scale);
        setLeftBack((y + x - rotation) * scale);
    }
    
    public void stop() {
        setRightFront(0.0);
        setRightBack(0.0);
        setLeftFront(0.0);
        setLeftBack(0.0);
    }
    
    private double deadband(double input) {
        if (input >= MIN_DEADBAND && input <= MAX_DEADBAND) {
            return 0.0;
        } else {
            return input;
        }
    }
    
    private void setRightFront(double setpoint) {
        rightFrontMotor.set(scale(setpoint, RIGHT_FRONT_MAX, RIGHT_FRONT_MIN, RIGHT_FRONT_SCALE));
    }
    
    private void setRightBack(double setpoint) {
        rightBackMotor.set(scale(setpoint, RIGHT_BACK_MAX, RIGHT_BACK_MIN, RIGHT_BACK_SCALE));
    }
    
    private void setLeftFront(double setpoint) {
        leftFrontMotor.set(scale(setpoint, LEFT_FRONT_MAX, LEFT_FRONT_MIN, LEFT_FRONT_SCALE));
    }
    
    private void setLeftBack(double setpoint) {
        leftBackMotor.set(scale(setpoint, LEFT_BACK_MAX, LEFT_BACK_MIN, LEFT_BACK_SCALE));
    }
    
    private boolean getLimitSwitch() {
        return DigitalModule.getInstance(LIMIT_SWITCH_SLOT).getDIO(LIMIT_SWITCH_CHANNEL);
    }
    
    private double scale(double original, double max, double min, double scale) {
        return ((max - min) * original) + (min * signum(original));
    }
    
    private int signum(double n) {
        if (n > 0) {
            return 1;
        } else if (n < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
