package com.bhrobotics.widowmaker.model;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    private static final int STOP = 1;
    private static final int FIRE = 2;
    private static final int DRIVE_STICK = 1;
    private static final int AIM_STICK = 2;

    private DriverStation driverStation = DriverStation.getInstance();
    private Joystick driveStick = new Joystick(DRIVE_STICK);
    private Joystick aimStick = new Joystick(AIM_STICK);

    public boolean getStop() {
        return driverStation.getDigitalIn(STOP);
    }

    public boolean getFire() {
        return driverStation.getDigitalIn(FIRE);
    }

    // Joystick controls, probably will be changed.
    public double getX() { return driveStick.getX(); }
    public double getY() { return driveStick.getY(); }
    public double getRotation() { return aimStick.getX(); }
}