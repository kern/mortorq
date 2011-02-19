package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

class MecanumDriveListener implements Listener {
    private static final int MOTOR_SLOT   = 6;
    private static final int ENCODER_SLOT = 4;
    
    private static final double SCALE_FAST = 1.0;
    private static final double SCALE_SLOW = 0.5;
    
    private static final double MAX_PWM      = 1.0;
    private static final double MIN_PWM      = -1.0;
    private static final double MAX_DEADBAND = 0.2;
    private static final double MIN_DEADBAND = -0.2;
    
    private static final double KP                 = 0.4;
    private static final double KI                 = 0.0;
    private static final double KD                 = 0.0;
    private static final double DISTANCE_PER_PULSE = 1.0 / 5600.0;
    
    private static final int RIGHT_FRONT_MOTOR    = 5;
    private static final int RIGHT_FRONT_SIDE_A   = 5;
    private static final int RIGHT_FRONT_SIDE_B   = 6;
    private static final double RIGHT_FRONT_SCALE = -1.0;
    
    private static final int RIGHT_BACK_MOTOR    = 1;
    private static final int RIGHT_BACK_SIDE_A   = 9;
    private static final int RIGHT_BACK_SIDE_B   = 10;
    private static final double RIGHT_BACK_SCALE = -1.0;
    
    private static final int LEFT_FRONT_MOTOR    = 6;
    private static final int LEFT_FRONT_SIDE_A   = 3;
    private static final int LEFT_FRONT_SIDE_B   = 4;
    private static final double LEFT_FRONT_SCALE = 1.0;
    
    private static final int LEFT_BACK_MOTOR    = 2;
    private static final int LEFT_BACK_SIDE_A   = 7;
    private static final int LEFT_BACK_SIDE_B   = 8;
    private static final double LEFT_BACK_SCALE = 1.0;
    
    Encoder rightFrontEncoder;
    Encoder rightBackEncoder;
    Encoder leftFrontEncoder;
    Encoder leftBackEncoder;
    
    RateJaguar rightFrontMotor = new RateJaguar(MOTOR_SLOT, RIGHT_FRONT_MOTOR);
    RateJaguar rightBackMotor  = new RateJaguar(MOTOR_SLOT, RIGHT_BACK_MOTOR);
    RateJaguar leftFrontMotor  = new RateJaguar(MOTOR_SLOT, LEFT_FRONT_MOTOR);
    RateJaguar leftBackMotor   = new RateJaguar(MOTOR_SLOT, LEFT_BACK_MOTOR);
    
    PIDController rightFrontController;
    PIDController rightBackController;
    PIDController leftFrontController;
    PIDController leftBackController;
    
    public MecanumDriveListener() {
        DigitalInput rightFrontSideA = new DigitalInput(ENCODER_SLOT, RIGHT_FRONT_SIDE_A);
        DigitalInput rightFrontSideB = new DigitalInput(ENCODER_SLOT, RIGHT_FRONT_SIDE_B);
        
        DigitalInput rightBackSideA = new DigitalInput(ENCODER_SLOT, RIGHT_BACK_SIDE_A);
        DigitalInput rightBackSideB = new DigitalInput(ENCODER_SLOT, RIGHT_BACK_SIDE_B);
        
        DigitalInput leftFrontSideA = new DigitalInput(ENCODER_SLOT, LEFT_FRONT_SIDE_A);
        DigitalInput leftFrontSideB = new DigitalInput(ENCODER_SLOT, LEFT_FRONT_SIDE_B);
        
        DigitalInput leftBackSideA = new DigitalInput(ENCODER_SLOT, LEFT_BACK_SIDE_A);
        DigitalInput leftBackSideB = new DigitalInput(ENCODER_SLOT, LEFT_BACK_SIDE_B);
        
        // Later, I will explain my hatred of whoever made this code necessary. I will
        // hunt you down with a large pack of rabid dogs (and possibly squirrels) and
        // gnaw off your face. Thank you, that is all.
        rightFrontEncoder = new Encoder(rightFrontSideA, rightFrontSideB, false, Encoder.EncodingType.k1X);
        rightBackEncoder  = new Encoder(rightBackSideA, rightBackSideB, false, Encoder.EncodingType.k1X);
        rightBackEncoder  = new Encoder(rightBackSideA, rightBackSideB, false, Encoder.EncodingType.k1X);
        leftFrontEncoder  = new Encoder(leftFrontSideA, leftFrontSideB, false, Encoder.EncodingType.k1X);
        leftFrontEncoder  = new Encoder(leftFrontSideA, leftFrontSideB, false, Encoder.EncodingType.k1X);
        leftBackEncoder   = new Encoder(leftBackSideA, leftBackSideB, false, Encoder.EncodingType.k1X);
        leftBackEncoder   = new Encoder(leftBackSideA, leftBackSideB, false, Encoder.EncodingType.k1X);
        leftBackEncoder   = new Encoder(leftBackSideA, leftBackSideB, false, Encoder.EncodingType.k1X);
        
        rightFrontController = new PIDController(KP, KI, KD, rightFrontEncoder, rightFrontMotor);
        rightBackController  = new PIDController(KP, KI, KD, rightBackEncoder, rightBackMotor);
        leftFrontController  = new PIDController(KP, KI, KD, leftFrontEncoder, leftFrontMotor);
        leftBackController   = new PIDController(KP, KI, KD, leftBackEncoder, leftBackMotor);
        
        rightFrontEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        rightFrontEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        rightFrontEncoder.start();
        rightFrontController.enable();
        
        rightBackEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        rightBackEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        rightBackEncoder.start();
        rightBackController.enable();
        
        leftFrontEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        leftFrontEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        leftFrontEncoder.start();
        leftFrontController.enable();
        
        leftBackEncoder.setDistancePerPulse(DISTANCE_PER_PULSE);
        leftBackEncoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate);
        leftBackEncoder.start();
        leftBackController.enable();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.startsWith("changeJoystick")) {
            Joystick joystick = (Joystick) event.getData("joystick");
            
            if (joystick.getTrigger()) {
                drive(joystick.getX(), joystick.getY(), joystick.getZ(), SCALE_SLOW);
            } else {
                drive(joystick.getX(), joystick.getY(), joystick.getZ(), SCALE_FAST);
            }
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
        double rightFrontSetpoint = applyBounds(y + x + rotation);
        double rightBackSetpoint  = applyBounds(y - x + rotation);
        double leftFrontSetpoint  = applyBounds(y - x - rotation);
        double leftBackSetpoint   = applyBounds(y + x - rotation);
        
        setRightFront(rightFrontSetpoint * scale);
        setRightBack(rightBackSetpoint * scale);
        setLeftFront(leftFrontSetpoint * scale);
        setLeftBack(leftBackSetpoint * scale);
    }
    
    public void stop() {
        setRightFront(0.0);
        setRightBack(0.0);
        setLeftFront(0.0);
        setLeftBack(0.0);
    }
    
    private double applyBounds(double input) {
        input = Math.min(MAX_PWM, Math.max(MIN_PWM, input));
        
        if (input >= MIN_DEADBAND && input <= MAX_DEADBAND) {
            input = 0.0;
        }
        
        return input;
    }
    
    private void setRightFront(double setpoint) {
        rightFrontController.setSetpoint(setpoint * RIGHT_FRONT_SCALE);
    }
    
    private void setRightBack(double setpoint) {
        rightBackController.setSetpoint(setpoint * RIGHT_BACK_SCALE);
    }
    
    private void setLeftFront(double setpoint) {
        leftFrontController.setSetpoint(setpoint * LEFT_FRONT_SCALE);
    }
    
    private void setLeftBack(double setpoint) {
        leftBackController.setSetpoint(setpoint * LEFT_BACK_SCALE);
    }
}