package com.bhrobotics.widowmaker.model;

// Controls the robot's drive train. It takes in input in the form of values
// ranging from -1 (backwards or ccw) to 1 (forwards or cw). This does not move
// the robot, this merely stores values for the view(s) to use.
public class DriveTrain {

    private static double rightFront;
    private static double rightBack;
    private static double leftFront;
    private static double leftBack;

    // Stop all drive motors and engage any brakes.
    public static void stopped() {
        rightFront = 0;
        rightBack = 0;
        leftFront = 0;
        leftBack = 0;
    }

    public static double getRightFront() { return rightFront; }
    public static double getRightBack() { return rightBack; }
    public static double getLeftFront() { return leftFront;}
    public static double getLeftBack() { return leftBack; }

    public static double getRight() { return rightFront; }
    public static double getLeft() { return leftFront; }

    // "Complicated" mecanum style drive code. It basically just adds and
    // subtracts the rotation from the standard arcade drive settings. X  is the
    // new strafe speed, in range [-1..1]. Y the new forward speed, in range
    // [-1..1]. Rotation the new turn rate, in range [-1..1].
    public static void mecanum(double x, double y, double rotation) {
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
}