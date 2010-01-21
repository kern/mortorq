package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * Controls the robot's drive train. It listens for changes to
 * the desired drive characteristics, and will cause the robot's drive
 * mechanisms to conform to the requested drive characteristics as
 * much as possible.
 **/
public class DriveTrain implements OiListener {

    private SpeedController _rightFrontMotor;
    private SpeedController _rightBackMotor;
    private SpeedController _leftFrontMotor;
    private SpeedController _leftBackMotor;

    /**
     * Create a new drive train using the given motors for a
     * mechanum-style drive.
     * @param rightFront the motor on the front right side
     * @param rightBack the motor on the back right side
     * @param leftFront the motor on the front left side
     * @param leftBack the motor on the back left side
     **/
    public DriveTrain(SpeedController rightFront, SpeedController rightBack,
            SpeedController leftFront, SpeedController leftBack) {

        // Prevent illegal setup
        if (rightFront == null) {
            throw new IllegalArgumentException("right front motor is null!");
        }

        if (rightBack == null) {
            throw new IllegalArgumentException("right back motor is null!");
        }

        if (leftFront == null) {
            throw new IllegalArgumentException("left front motor is null!");
        }

        if (leftBack == null) {
            throw new IllegalArgumentException("left back motor is null!");
        }

        // Save motor values for later
        _rightFrontMotor = rightFront;
        _rightBackMotor = rightBack;
        _leftFrontMotor = leftFront;
        _leftBackMotor = leftBack;
    }

    public void emergencyStop() {
        // Stop all drive motors and engage any brakes.
        _rightFrontMotor.set(0);
        _rightBackMotor.set(0);
        _leftFrontMotor.set(0);
        _leftBackMotor.set(0);
    }

    /**
     * User has changed the driving controls to a new speed, strafe,
     * or rotation. The speed gives the desired forward velocity of the
     * robot, with zero indicating stopped and negative values indicating
     * driving in reverse. The strafe gives the desired sideways velocity
     * of the  robot, with zero indicating stopped and negative values
     * indication driving to the left. The rotation gives the desired angular
     * velocity of the robot, with zero indicating no turn, positive values
     * indicating a clockwise (right) turn, and negative values indicating a
     * counterclockwise (left) turn.
     *
     * @param speed the new speed, in range [-1..1]
     * @param strafe the new strafe, in range [-1..1]
     * @param rotation the new rotation, in range [-1..1]
     **/
    public void move(double speed, double strafe, double rotation) {

        // Convert speed, strafe, and rotation to mechanum motor speed settings
        double rightFrontTarget = speed + strafe + rotation;
        double rightBackTarget = speed - strafe + rotation;
        double leftFrontTarget = speed - strafe - rotation;
        double leftBackTarget = speed + strafe - rotation;

        rightFrontTarget = Math.min(1.0, Math.max(-1.0, rightFrontTarget));
        rightBackTarget = Math.min(1.0, Math.max(-1.0, rightBackTarget));
        leftFrontTarget = Math.min(1.0, Math.max(-1.0, leftFrontTarget));
        leftBackTarget = Math.min(1.0, Math.max(-1.0, leftBackTarget));

        _rightFrontMotor.set(rightFrontTarget);
        _rightBackMotor.set(rightBackTarget);
        _leftFrontMotor.set(leftFrontTarget);
        _leftBackMotor.set(leftBackTarget);
    }
}
