/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.SpeedController;


/**
 * Class that controls the robot's drive train. It listens for changes to
 * the desired drive characteristics, and will cause the robot's drive
 * mechanisms to conform to the requested drive characteristics as
 * much as possible.
 *
 * @author Administrator
 */
public class DriveTrain implements OiListener {
    private SpeedController _rightMotor;
    private SpeedController _leftMotor;


    /**
     * Create a new drive train using the given motors for a
     * tank-style drive train.
     * @param right the motor on the right side
     * @param left the motor on the left side
     */
    public DriveTrain(SpeedController right, SpeedController left) {
        // Prevent illegal setup
        if(right == null) {
            throw new IllegalArgumentException("right motor is null!");
        }
        if(right == null) {
            throw new IllegalArgumentException("left motor is null!");
        }
        // Save needed values for later
        _rightMotor = right;
        _leftMotor = left;
    }


    /**
     * An emergency stop has been requested.
     */
    public void emergencyStop() {
        // Stop all drive motors and engage any brakes.
        _rightMotor.set(0);
        _leftMotor.set(0);
    }


    /**
     * User has changed the driving controls to a new speed or turn rate.
     * The speed gives the desired forward velocity of the robot, with
     * zero indicating stopped and negative values indicating driving in
     * reverse. The turn rate gives the desired angular velocity of the
     * robot, with zero indicating no turn, positive values indicating a
     * clockwise (right) turn, and negative values indicating a
     * counterclockwise (left) turn.
     *
     * @param speed the new speed, in range [-1..1]
     * @param turnRate the new turn rate, in range [-1..1]
     */
    public void move(double speed, double turnRate) {
        // Simple algorithm: Motors attempt to reach desired settings
        // immediately.
        // Alternate algorithm, should it be desired: have a target
        // speed for each motor, and gradually ramp the motor speed up
        // or down until it reaches that speed.

        // Convert speed and turn rate to motor speed settings
        double leftTarget = speed + turnRate;
        double rightTarget = speed - turnRate;
        leftTarget = Math.min(1.0, Math.max(-1.0, leftTarget));
        rightTarget = Math.min(1.0, Math.max(-1.0, rightTarget));

        // Set motor speeds
        _rightMotor.set(rightTarget);
        _leftMotor.set(leftTarget);
    }
}
