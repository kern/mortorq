/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bhrobotics.widowmaker;


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

    OperatorInterface(Widowmaker robot) {
        super(robot);
    }


    /**
     * @return the desired time interval (in microseconds, not milliseconds)
     * between calls to periodic()
     */
    protected double getPeriod() {
        return PERIOD;
    }


    /**
     * Called periodically while this operator is in control.
     */
    protected void periodic() {
    }
}
