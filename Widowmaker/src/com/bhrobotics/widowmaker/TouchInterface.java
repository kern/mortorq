package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.OperatorInterface;

public class TouchInterface extends OperatorInterface {

    private static final int DRIVE_STICK = 0;

    //**************************************************************************
    // Joystick
    //**************************************************************************

    public double getStrafe() { return joysticks[DRIVE_STICK].getX(); }
    public double getSpeed() { return joysticks[DRIVE_STICK].getY(); }
    public double getRotation() { return joysticks[DRIVE_STICK].getZ(); }

    public boolean getCarneyAuto() {
         return false;
    }

    public boolean getKick() {
        return joyButton(1) || inputOn(1, 3);
    }

    public boolean getRetract() {
        return false;
    }

    public boolean getExtend() {
        return false;
    }

    public boolean getCompressorAuto() {
        return false;
    }

    public boolean getCompressor() {
        return false;
    }

    public boolean getDeflector() {
        return false;
    }

    public double getRoller() {
        return 0.0;
    }

    public boolean getDriveAuto() {
        return true;
    }

    public double getFrontLeft() {
        return 0.0;
    }

    public double getFrontRight() {
        return 0.0;
    }

    public double getBackLeft() {
        return 0.0;
    }

    public double getBackRight() {
        return 0.0;
    }
}