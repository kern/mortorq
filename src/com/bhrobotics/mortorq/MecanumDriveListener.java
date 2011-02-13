package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;

class MecanumDriveListener implements Listener {
    private static final boolean USE_PID = false;
    
    private static final int MOTOR_SLOT   = 6;
    private static final int ENCODER_SLOT = 4;
    
    private static final int RIGHT_FRONT = 2;
    private static final int RIGHT_BACK  = 6;
    private static final int LEFT_FRONT  = 1;
    private static final int LEFT_BACK   = 5;
    
    private static final double SCALE_RIGHT_FRONT = 1.0;
    private static final double SCALE_RIGHT_BACK  = 1.0;
    private static final double SCALE_LEFT_FRONT  = 1.0;
    private static final double SCALE_LEFT_BACK   = 1.0;
    
    private static final double SCALE_FAST = 1.0;
    private static final double SCALE_SLOW = 0.5;
    
    private static final double MAX_PWM      = 1.0;
    private static final double MIN_PWM      = -1.0;
    private static final double MAX_DEADBAND = 0.2;
    private static final double MIN_DEADBAND = -0.2;
    
    private static final int SIDE_A_RIGHT_FRONT            = 4;
    private static final int SIDE_B_RIGHT_FRONT            = 5;
    private static final boolean REVERSE_DIR_RIGHT_FRONT   = false;
    private static final int KP_RIGHT_FRONT                = 1;
    private static final int KI_RIGHT_FRONT                = 0;
    private static final int KD_RIGHT_FRONT                = 0;
    private static final double PULSE_DISTANCE_RIGHT_FRONT = 1.0;
    private static final double MAX_RATE_RIGHT_FRONT       = 1000;
    private static final double MIN_RATE_RIGHT_FRONT       = -1000;
    
    private static final int SIDE_A_RIGHT_BACK            = 6;
    private static final int SIDE_B_RIGHT_BACK            = 7;
    private static final boolean REVERSE_DIR_RIGHT_BACK   = false;
    private static final int KP_RIGHT_BACK                = 1;
    private static final int KI_RIGHT_BACK                = 0;
    private static final int KD_RIGHT_BACK                = 0;
    private static final double PULSE_DISTANCE_RIGHT_BACK = 1.0;
    private static final double MAX_RATE_RIGHT_BACK       = 1000;
    private static final double MIN_RATE_RIGHT_BACK       = -1000;
    
    private static final int SIDE_A_LEFT_FRONT            = 8;
    private static final int SIDE_B_LEFT_FRONT            = 9;
    private static final boolean REVERSE_DIR_LEFT_FRONT   = false;
    private static final int KP_LEFT_FRONT                = 1;
    private static final int KI_LEFT_FRONT                = 0;
    private static final int KD_LEFT_FRONT                = 0;
    private static final double PULSE_DISTANCE_LEFT_FRONT = 1.0;
    private static final double MAX_RATE_LEFT_FRONT       = 1000;
    private static final double MIN_RATE_LEFT_FRONT       = -1000;
    
    private static final int SIDE_A_LEFT_BACK            = 10;
    private static final int SIDE_B_LEFT_BACK            = 11;
    private static final boolean REVERSE_DIR_LEFT_BACK   = false;
    private static final int KP_LEFT_BACK                = 1;
    private static final int KI_LEFT_BACK                = 0;
    private static final int KD_LEFT_BACK                = 0;
    private static final double PULSE_DISTANCE_LEFT_BACK = 1.0;
    private static final double MAX_RATE_LEFT_BACK       = 1000;
    private static final double MIN_RATE_LEFT_BACK       = -1000;
    
    RateEncoder rightFrontEncoder = USE_PID ? new RateEncoder(ENCODER_SLOT, SIDE_A_RIGHT_FRONT, ENCODER_SLOT, SIDE_B_RIGHT_FRONT, REVERSE_DIR_RIGHT_FRONT) : null;
    RateEncoder rightBackEncoder  = USE_PID ? new RateEncoder(ENCODER_SLOT, SIDE_A_RIGHT_BACK, ENCODER_SLOT, SIDE_B_RIGHT_BACK, REVERSE_DIR_RIGHT_BACK) : null;
    RateEncoder leftFrontEncoder  = USE_PID ? new RateEncoder(ENCODER_SLOT, SIDE_A_LEFT_FRONT, ENCODER_SLOT, SIDE_B_LEFT_FRONT, REVERSE_DIR_LEFT_FRONT) : null;
    RateEncoder leftBackEncoder   = USE_PID ? new RateEncoder(ENCODER_SLOT, SIDE_A_LEFT_BACK, ENCODER_SLOT, SIDE_B_LEFT_BACK, REVERSE_DIR_LEFT_BACK) : null;
    
    PIDJaguar rightFrontMotor = new PIDJaguar(MOTOR_SLOT, RIGHT_FRONT);
    PIDJaguar rightBackMotor  = new PIDJaguar(MOTOR_SLOT, RIGHT_BACK);
    PIDJaguar leftFrontMotor  = new PIDJaguar(MOTOR_SLOT, LEFT_FRONT);
    PIDJaguar leftBackMotor   = new PIDJaguar(MOTOR_SLOT, LEFT_BACK);
    
    PIDController rightFrontController = USE_PID ? new PIDController(KP_RIGHT_FRONT, KI_RIGHT_FRONT, KD_RIGHT_FRONT, rightFrontEncoder, rightFrontMotor) : null;
    PIDController rightBackController  = USE_PID ? new PIDController(KP_RIGHT_BACK, KI_RIGHT_BACK, KD_RIGHT_BACK, rightBackEncoder, rightBackMotor) : null;
    PIDController leftFrontController  = USE_PID ? new PIDController(KP_LEFT_FRONT, KI_LEFT_FRONT, KD_LEFT_FRONT, leftFrontEncoder, leftFrontMotor) : null;
    PIDController leftBackController   = USE_PID ? new PIDController(KP_LEFT_BACK, KI_LEFT_BACK, KD_LEFT_BACK, leftBackEncoder, leftBackMotor) : null;
    
    public MecanumDriveListener() {
        rightFrontEncoder.setDistancePerPulse(PULSE_DISTANCE_RIGHT_FRONT);
        rightFrontEncoder.start();
        
        rightBackEncoder.setDistancePerPulse(PULSE_DISTANCE_RIGHT_BACK);
        rightBackEncoder.start();
        
        leftFrontEncoder.setDistancePerPulse(PULSE_DISTANCE_LEFT_FRONT);
        leftFrontEncoder.start();
        
        leftBackEncoder.setDistancePerPulse(PULSE_DISTANCE_LEFT_BACK);
        leftBackEncoder.start();
        
        rightFrontController.setInputRange(MIN_RATE_RIGHT_FRONT, MAX_RATE_RIGHT_FRONT);
        rightBackController.setInputRange(MIN_RATE_RIGHT_BACK, MAX_RATE_RIGHT_BACK);
        leftFrontController.setInputRange(MIN_RATE_LEFT_FRONT, MAX_RATE_LEFT_FRONT);
        leftBackController.setInputRange(MIN_RATE_LEFT_BACK, MAX_RATE_LEFT_BACK);
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.startsWith("updateJoystick")) {
            Joystick joystick = (Joystick) event.getData("joystick");
            
            if (joystick.getTrigger()) {
                drive(joystick.getX(), joystick.getY(), joystick.getZ(), SCALE_SLOW);
            } else {
                drive(joystick.getX(), joystick.getY(), joystick.getZ(), SCALE_FAST);
            }
        } else if (name.startsWith("updateMotor")) {
            enablePID();
            
            double setpoint = ((Double) event.getData("value")).doubleValue();
            
            if (name == "updateMotorRightFront") {
                rightFrontController.setSetpoint(setpoint);
            } else if (name == "updateMotorRightBack") {
                rightBackController.setSetpoint(setpoint);
            } else if (name == "updateMotorLeftFront") {
                leftFrontController.setSetpoint(setpoint);
            } else if (name == "updateMotorLeftBack") {
                leftBackController.setSetpoint(setpoint);
            }
        } else if (name == "stopMotors") {
            stop();
        }
    }
    
    public void drive(double x, double y, double rotation) {
        drive(x, y, rotation, SCALE_FAST);
    }
    
    public void drive(double x, double y, double rotation, double scale) {
        enablePID();
        
        double rightFrontSetpoint = applyBounds(y + x + rotation);
        double rightBackSetpoint  = applyBounds(y - x + rotation);
        double leftFrontSetpoint  = applyBounds(y - x - rotation);
        double leftBackSetpoint   = applyBounds(y + x - rotation);
        
        if(USE_PID) {
            rightFrontController.setSetpoint(rightFrontSetpoint * SCALE_RIGHT_FRONT * scale);
            rightBackController.setSetpoint(rightBackSetpoint * SCALE_RIGHT_BACK * scale);
            leftFrontController.setSetpoint(leftFrontSetpoint * SCALE_LEFT_FRONT * scale);
            leftBackController.setSetpoint(leftBackSetpoint * SCALE_LEFT_BACK * scale);
        } else {
            rightFrontMotor.set(rightFrontSetpoint * SCALE_RIGHT_FRONT * scale);
            rightBackMotor.set(rightBackSetpoint * SCALE_RIGHT_BACK * scale);
            leftFrontMotor.set(leftFrontSetpoint * SCALE_LEFT_FRONT * scale);
            leftBackMotor.set(leftBackSetpoint * SCALE_LEFT_BACK * scale);
        }
    }
    
    public void stop() {
        disablePID();
        
        rightFrontMotor.set(0.0);
        rightBackMotor.set(0.0);
        leftFrontMotor.set(0.0);
        leftBackMotor.set(0.0);
    }
    
    private void enablePID() {
        rightFrontController.enable();
        rightBackController.enable();
        leftFrontController.enable();
        leftBackController.enable();
    }
    
    private void disablePID() {
        rightFrontController.disable();
        rightBackController.disable();
        leftFrontController.disable();
        leftBackController.disable();
        
        rightFrontController.setSetpoint(0.0);
        rightBackController.setSetpoint(0.0);
        leftFrontController.setSetpoint(0.0);
        leftBackController.setSetpoint(0.0);
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    private double applyBounds(double input) {
        input = Math.min(MAX_PWM, Math.max(MIN_PWM, input));
        
        if(input >= MIN_DEADBAND && input <= MAX_DEADBAND) {
            input = 0.0;
        }
        
        return input;
    }
}