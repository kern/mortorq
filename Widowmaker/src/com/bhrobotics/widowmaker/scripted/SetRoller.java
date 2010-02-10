/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bhrobotics.widowmaker.scripted;

import com.bhrobotics.widowmaker.models.Roller;

/**
 * Script instruction to set the roller speed/status
 */
public class SetRoller implements Instruction {
    /** Value indicating the roller should be running. */
    public static final int ON = 1;
    /** Value indicating the roller should be stopped. */
    public static final int OFF = 0;

    private Roller roller;
    private int state = OFF;

    /**
     * Create a new instruction applying to the given roller, turning it
     * on or off.
     * @param _roller the roller to set
     * @param _state ON or OFF; the desired status of the roller
     */
    public SetRoller(Roller _roller, int _state) {
        if(roller == null) {
            throw new IllegalArgumentException("null roller!");
        }
        if((_state != ON) && (_state != OFF)) {
            throw new IllegalArgumentException(
                    "state (" + _state + ") must be ON or OFF");
        }
        roller = _roller;
        state = _state;
    }

    /**
     * Turn the roller on or off.
     * @return true, as this instruction always completes
     */
    public boolean execute() {
        if(state == ON) {
            roller.start();
        } else if(state == OFF) {
            roller.stop();
        }
        return true;
    }

}
