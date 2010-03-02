package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.OperatorInterface;

public class TouchInterface extends OperatorInterface {

    private static final int DRIVE_STICK = 0;

    //**************************************************************************
    // Joystick
    //**************************************************************************

    public double getX() { return joysticks[DRIVE_STICK].getX(); }
    public double getY() { return joysticks[DRIVE_STICK].getY(); }
    public double getZ() { return joysticks[DRIVE_STICK].getZ(); }

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

    public boolean getLeftDeflector() {
        return false;
    }

    public boolean getRightDeflector() {
        return false;
    }

    public int getRollerDirection() {
        return 0;
    }

    public double getRollerSpeed() {
        return 0.0;
    }

    public int getFrontLeftDirection() {
        return 0;
    }

    public double getFrontLeftSpeed() {
        return 0.0;
    }

    public int getFrontRightDirection() {
        return 0;
    }

    public double getFrontRightSpeed() {
        return 0.0;
    }
    
    public int getBackLeftDirection() {
        return 0;
    }

    public double getBackLeftSpeed() {
        return 0.0;
    }

    public int getBackRightDirection() {
        return 0;
    }

    public double getBackRightSpeed() {
        return 0.0;
    }
}