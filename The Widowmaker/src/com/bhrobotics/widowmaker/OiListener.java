/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bhrobotics.widowmaker;

/**
 *
 * @author Administrator
 */
public interface OiListener {
    /**
     * An emergency stop has been requested.
     */
    public void emergencyStop();

            
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
    public void move(double speed, double turnRate);
}
