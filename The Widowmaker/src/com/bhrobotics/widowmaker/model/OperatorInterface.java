package com.bhrobotics.widowmaker.model;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    private static final int EMERGENCY_STOP = 1;
    private static final int FIRE = 2;
    private static final int DRIVE_STICK = 1;
    private static final int AIM_STICK = 2;

    private static DriverStation driverStation = DriverStation.getInstance();
    private static Joystick driveStick = new Joystick(DRIVE_STICK);
    private static Joystick aimStick = new Joystick(AIM_STICK);

    // Used for vision tracking possibly.
    public static DriverStation.Alliance getAlliance() {
        return driverStation.getAlliance();
    }

    public static boolean getEmergencyStop() {
        return driverStation.getDigitalIn(EMERGENCY_STOP);
    }

    public static boolean getFire() {
        return driverStation.getDigitalIn(FIRE);
    }

    // Joystick controls, probably will be changed.
    public static double getX() { return driveStick.getX(); }
    public static double getY() { return driveStick.getY(); }
    public static double getRotation() { return aimStick.getX(); }
}