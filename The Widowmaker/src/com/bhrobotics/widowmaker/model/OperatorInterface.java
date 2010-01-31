package com.bhrobotics.widowmaker.model;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    private static OperatorInterface instance = new OperatorInterface();

    private static final int EMERGENCY_STOP = 1;
    private static final int DRIVE_STICK = 1;
    private static final int AIM_STICK = 2;

    private DriverStation driverStation;
    private Joystick driveStick;
    private Joystick aimStick;

    public OperatorInterface() {
        driverStation = DriverStation.getInstance();
        driveStick = new Joystick(DRIVE_STICK);
        aimStick = new Joystick(AIM_STICK);
    }

    // Allows this class to be a singleton class.
    public static OperatorInterface getInstance() {
        return OperatorInterface.instance;
    }

    // Used for vision tracking possibly.
    public DriverStation.Alliance getAlliance() {
        return driverStation.getAlliance();
    }
    
    public boolean getEmergencyStop() {
        return driverStation.getDigitalIn(EMERGENCY_STOP);
    }

    // Joystick controls, probably will be changed.
    public double getX() { return driveStick.getX(); }
    public double getY() { return driveStick.getY(); }
    public double getRotation() { return aimStick.getX(); }
}