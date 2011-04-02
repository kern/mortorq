package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;

class MecanumDriveListener implements Listener {
    private static final int MOTOR_SLOT   = 4;
    
    private static final double SCALE_FAST = 1.0;
    private static final double SCALE_SLOW = 0.5;
    
    private static final double MAX_DEADBAND = 0.2;
    private static final double MIN_DEADBAND = -0.2;
    
    private static final double X_SCALE = 1.0;
    private static final double Y_SCALE = 1.0;
    private static final double Z_SCALE = 0.6;
    
    private static final int RIGHT_FRONT_MOTOR       = 2;
    private static final boolean RIGHT_FRONT_REVERSE = true;
    private static final double RIGHT_FRONT_MAX      = 1.0;
    private static final double RIGHT_FRONT_MIN      = 0.14285714285714285;
    
    private static final int RIGHT_BACK_MOTOR       = 6;
    private static final boolean RIGHT_BACK_REVERSE = true;
    private static final double RIGHT_BACK_MAX      = 1.0;
    private static final double RIGHT_BACK_MIN      = 0.13333333333333333;
    
    private static final int LEFT_FRONT_MOTOR       = 1;
    private static final boolean LEFT_FRONT_REVERSE = false;
    private static final double LEFT_FRONT_MAX      = 1.0;
    private static final double LEFT_FRONT_MIN      = 0.14285714285714285;
    
    private static final int LEFT_BACK_MOTOR       = 5;
    private static final boolean LEFT_BACK_REVERSE = false;
    private static final double LEFT_BACK_MAX      = 1.0;
    private static final double LEFT_BACK_MIN      = 0.12;
    
    private static final int LIMIT_SWITCH_SLOT    = 4;
    private static final int LIMIT_SWITCH_CHANNEL = 1;
    
    Victor rightFrontMotor = new Victor(MOTOR_SLOT, RIGHT_FRONT_MOTOR);
    Victor rightBackMotor  = new Victor(MOTOR_SLOT, RIGHT_BACK_MOTOR);
    Victor leftFrontMotor  = new Victor(MOTOR_SLOT, LEFT_FRONT_MOTOR);
    Victor leftBackMotor   = new Victor(MOTOR_SLOT, LEFT_BACK_MOTOR);
    
    public MecanumDriveListener() {
        stop();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.startsWith("changeJoystick")) {
            Joystick joystick = (Joystick) event.getData("joystick");
            
            double x = deadband(joystick.getX()) * X_SCALE;
            double y = deadband(joystick.getY()) * Y_SCALE;
            double z = deadband(joystick.getZ()) * Z_SCALE;
            
            if (joystick.getRawButton(2)) {
                x *= -1.0;
                y *= -1.0;
            }
            
            if (joystick.getTrigger() || getLimitSwitch() || joystick.getRawButton(2)) {
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
        rightFrontMotor.set(scale(setpoint, RIGHT_FRONT_MAX, RIGHT_FRONT_MIN, RIGHT_FRONT_REVERSE));
    }
    
    private void setRightBack(double setpoint) {
        rightBackMotor.set(scale(setpoint, RIGHT_BACK_MAX, RIGHT_BACK_MIN, RIGHT_BACK_REVERSE));
    }
    
    private void setLeftFront(double setpoint) {
        leftFrontMotor.set(scale(setpoint, LEFT_FRONT_MAX, LEFT_FRONT_MIN, LEFT_FRONT_REVERSE));
    }
    
    private void setLeftBack(double setpoint) {
        leftBackMotor.set(scale(setpoint, LEFT_BACK_MAX, LEFT_BACK_MIN, LEFT_BACK_REVERSE));
    }
    
    private boolean getLimitSwitch() {
        return DigitalModule.getInstance(LIMIT_SWITCH_SLOT).getDIO(LIMIT_SWITCH_CHANNEL);
    }
    
    private double scale(double original, double max, double min, boolean reverse) {
        double multiple = reverse ? -1.0 : 1.0;
        return (((max - min) * original) + (min * signum(original))) * multiple;
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
