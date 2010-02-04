package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface implements Model {

    //**************************************************************************
    // Inputs
    //**************************************************************************

    private static final int STOP = 1;
    private static final int FIRE = 2;
    private static final int DRIVE_STICK = 1;
    private static final int AIM_STICK = 2;

    private Joystick driveStick = new Joystick(DRIVE_STICK);
    private Joystick aimStick = new Joystick(AIM_STICK);

    //**************************************************************************
    // Interface
    //**************************************************************************

    // An operator interface does not need to be started or stopped; it is a
    // special kind of model.
    public void start() {}
    public void stop() {}

    //**************************************************************************
    // Getters
    //**************************************************************************

    public boolean getStop() {
        return DriverStation.getInstance().getDigitalIn(STOP);
    }

    public boolean getFire() {
        return DriverStation.getInstance().getDigitalIn(FIRE);
    }

    // Joystick controls
    public double getX() { return driveStick.getX(); }
    public double getY() { return driveStick.getY(); }
    public double getRotation() { return aimStick.getX(); }
}