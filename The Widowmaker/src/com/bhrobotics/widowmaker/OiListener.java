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
     * @param speed the new speed, in range TODO
     * @param turnRate the new turn rate, in range TODO
     */
    public void move(double speed, double turnRate);
}
