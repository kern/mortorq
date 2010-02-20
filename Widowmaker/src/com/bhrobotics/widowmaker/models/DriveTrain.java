package com.bhrobotics.widowmaker.models;

public class DriveTrain {

    private static final double DEADBAND = 0.1;

    //**************************************************************************
    // Motors
    //**************************************************************************

    private double rightFront;
    private double rightBack;
    private double leftFront;
    private double leftBack;

    //**************************************************************************
    // Rates
    //**************************************************************************

    private double rightFrontRate;
    private double rightBackRate;
    private double leftFrontRate;
    private double leftBackRate;

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
        double rightFrontTarget = deadband(y + x + rotation);
        double rightBackTarget = deadband(y - x + rotation);
        double leftFrontTarget = deadband(y - x - rotation);
        double leftBackTarget = deadband(y + x - rotation);

        if(rightFrontRate > Math.min(1.0, Math.max(-1.0, rightFrontTarget))) {
            rightFront += 0.05;
        }else if(rightFrontRate > Math.min(1.0, Math.max(-1.0, rightFrontTarget))) {
            rightFront -= 0.05;
        }

        rightBack = Math.min(1.0, Math.max(-1.0, rightBackTarget));
        leftFront = Math.min(1.0, Math.max(-1.0, leftFrontTarget));
        leftBack = Math.min(1.0, Math.max(-1.0, leftBackTarget));
    }

    //**************************************************************************
    // Internal
    //*************************************************************************

    private double deadband(double value) {
        if(value < DEADBAND && value > -DEADBAND) { return 0.0; }
        return value;
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
    public double getLeftFront() { return leftFront; }
    public double getLeftBack() { return leftBack; }

    //**************************************************************************
    // Setters
    //**************************************************************************

    public void setRightFrontRate(double rate) {
        rightFrontRate = rate;
    }
}