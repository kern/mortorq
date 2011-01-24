package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;

class MecanumDriveListener extends Listener {
    private static final boolean USE_PID = false;
    
    private static final int MOTOR_SLOT   = 6;
    private static final int ENCODER_SLOT = 4;
    
    private static final int RIGHT_FRONT = 4;
    private static final int RIGHT_BACK  = 3;
    private static final int LEFT_FRONT  = 5;
    private static final int LEFT_BACK   = 2;
    
    private static final double SCALE_RIGHT_FRONT = 1.0;
    private static final double SCALE_RIGHT_BACK  = 1.0;
    private static final double SCALE_LEFT_FRONT  = -1.0;
    private static final double SCALE_LEFT_BACK   = -1.0;
    
    private static final double MAX_PWM      = 1.0;
    private static final double MIN_PWM      = -1.0;
    private static final double MAX_DEADBAND = 0.2;
    private static final double MIN_DEADBAND = -0.2;
    
    private static final int SIDE_A_RIGHT_FRONT          = 1;
    private static final int SIDE_B_RIGHT_FRONT          = 2;
    private static final boolean REVERSE_DIR_RIGHT_FRONT = false;
    private static final int KP_RIGHT_FRONT              = 1;
    private static final int KI_RIGHT_FRONT              = 1;
    private static final int KD_RIGHT_FRONT              = 1;
    
    private static final int SIDE_A_RIGHT_BACK          = 3;
    private static final int SIDE_B_RIGHT_BACK          = 4;
    private static final boolean REVERSE_DIR_RIGHT_BACK = false;
    private static final int KP_RIGHT_BACK              = 1;
    private static final int KI_RIGHT_BACK              = 1;
    private static final int KD_RIGHT_BACK              = 1;
    
    private static final int SIDE_A_LEFT_FRONT          = 5;
    private static final int SIDE_B_LEFT_FRONT          = 6;
    private static final boolean REVERSE_DIR_LEFT_FRONT = false;
    private static final int KP_LEFT_FRONT              = 1;
    private static final int KI_LEFT_FRONT              = 1;
    private static final int KD_LEFT_FRONT              = 1;
    
    private static final int SIDE_A_LEFT_BACK          = 7;
    private static final int SIDE_B_LEFT_BACK          = 8;
    private static final boolean REVERSE_DIR_LEFT_BACK = false;
    private static final int KP_LEFT_BACK              = 1;
    private static final int KI_LEFT_BACK              = 1;
    private static final int KD_LEFT_BACK              = 1;
    
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
    
    public void handle(Event event) {
        Joystick joystick = (Joystick) event.getData("joystick");
        
        double x        = joystick.getX();
        double y        = joystick.getY();
        double rotation = joystick.getZ();
        
        double rightFrontSetpoint = applyBounds(y + x + rotation);
        double rightBackSetpoint  = applyBounds(y - x + rotation);
        double leftFrontSetpoint  = applyBounds(y - x - rotation);
        double leftBackSetpoint   = applyBounds(y + x - rotation);
        
        if(USE_PID) {
            rightFrontController.setSetpoint(rightFrontSetpoint * SCALE_RIGHT_FRONT);
            rightBackController.setSetpoint(rightBackSetpoint * SCALE_RIGHT_BACK);
            leftFrontController.setSetpoint(leftFrontSetpoint * SCALE_LEFT_FRONT);
            leftBackController.setSetpoint(leftBackSetpoint * SCALE_LEFT_BACK);
        } else {
            rightFrontMotor.set(rightFrontSetpoint * SCALE_RIGHT_FRONT);
            rightBackMotor.set(rightBackSetpoint * SCALE_RIGHT_BACK);
            leftFrontMotor.set(leftFrontSetpoint * SCALE_LEFT_FRONT);
            leftBackMotor.set(leftBackSetpoint * SCALE_LEFT_BACK);
        }
    }
    
    private double applyBounds(double input) {
        input = Math.min(MAX_PWM, Math.max(MIN_PWM, input));
        
        if(input >= MIN_DEADBAND && input <= MAX_DEADBAND) {
            input = 0.0;
        }
        
        return input;
    }
}
