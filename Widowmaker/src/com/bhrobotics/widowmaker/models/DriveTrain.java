package com.bhrobotics.widowmaker.models;

public class DriveTrain {

    //**************************************************************************
    // Motors
    //**************************************************************************

    private double rightFront;
    private double rightBack;
    private double leftFront;
    private double leftBack;

    //**************************************************************************
    // Interface
    //**************************************************************************

    // Stop all drive motors and engage any brakes when both starting and
    // stopping the robot. Simple safety procedure.
    public void start () { stop(); }
    public void stop() {
        rightFront = 0;
        rightBack = 0;
        leftFront = 0;
        leftBack = 0;
    }

    // "Complicated" mecanum style drive code. It basically just adds and
    // subtracts the rotation from the standard arcade drive settings. X  is the
    // new strafe speed, in range [-1..1]. Y the new forward speed, in range
    // [-1..1]. Rotation the new turn rate, in range [-1..1].
    public void mecanum(double x, double y, double rotation) {
        // Convert x, y, and rotation to mechanum motor speed settings
        double rightFrontTarget = y + x + rotation;
        double rightBackTarget = y - x + rotation;
        double leftFrontTarget = y - x - rotation;
        double leftBackTarget = y + x - rotation;

        rightFront = Math.min(1.0, Math.max(-1.0, rightFrontTarget));
        rightBack = Math.min(1.0, Math.max(-1.0, rightBackTarget));
        leftFront = Math.min(1.0, Math.max(-1.0, leftFrontTarget));
        leftBack = Math.min(1.0, Math.max(-1.0, leftBackTarget));
    }

    //**************************************************************************
    // Getters
    //**************************************************************************

    // Two wheel drive uses the front motors only.
    public double getRight() { return rightFront; }
    public double getLeft() { return leftFront; }

    // Four wheel drive uses the respective motor values.
    public double getRightFront() { return rightFront; }
    public double getRightBack() { return rightBack; }
    public double getLeftFront() { return leftFront;}
    public double getLeftBack() { return leftBack; }
}