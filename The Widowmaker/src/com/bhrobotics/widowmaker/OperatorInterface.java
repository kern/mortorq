/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.Joystick;


/**
 * Class that handles the station used by robot operators, containing
 * the joysticks, buttons, switches, etc., that are used to control
 * the robot's actions in teleoperated mode, as well as the E-Stop button
 * and other such controls.
 *
 * @author Administrator
 */
public class OperatorInterface extends Operator {
    
    // Time interval (microseconds) between calls to perioidic()
    private static final double PERIOD = 10000.0;

    private Joystick _driveStick;
    private Joystick _aimStick;


    /**
     * Create a new operator interface for the given robot.
     * @param robot the robot to control
     */
    OperatorInterface(Widowmaker robot) {
        super(robot);
    }


    /**
     * Set the driver's control.
     * @param stick the joystick that controls driving
     */
    void setDriveControl(Joystick stick) {
        _driveStick = stick;
    }

    /**
     * Set the driver's aiming control.
     * @param stick the joystick that controls aiming
     */
    void setAimControl(Joystick stick) {
        _aimStick = stick;
    }


    /**
     * @return the desired time interval (in microseconds, not milliseconds)
     * between calls to periodic()
     */
    protected double getPeriod() {
        return PERIOD;
    }


    /**
     * Called continuously while this operator is in control.
     */
    protected void continuous() {
        // Driving control
        notifyMove(_driveStick.getY(), _driveStick.getX(), _aimStick.getX());

        // Compressor control

        // Kicker control
    }


    /**
     * Called periodically while this operator is in control.
     */
    protected void periodic() {
    }
}
