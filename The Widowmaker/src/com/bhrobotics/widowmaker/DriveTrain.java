/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bhrobotics.widowmaker;

/**
 * Class that controls the robot's drive train. It listens for changes to
 * the desired drive characteristics, and will cause the robot's drive
 * mechanisms to conform to the requested drive characteristics as
 * much as possible.
 *
 * @author Administrator
 */
public class DriveTrain implements OiListener {
    /**
     * An emergency stop has been requested.
     */
    public void emergencyStop() {
        // Set all drive motors to speed zero; engage any brakes.
    }

    /**
     * User has changed the driving controls to a new speed or turn rate.
     * @param speed the new speed, in range TODO
     * @param turnRate the new turn rate, in range TODO
     */
    public void move(double speed, double turnRate) {
        // Convert speed and turn rate to motor speed settings
        // Set motor speeds
    }
}
