package com.bhrobotics.widowmaker.scripted;

import com.bhrobotics.widowmaker.models.Carney;

/**
 * Instruction to cause the robot to kick the ball
 */
public class Kick implements Instruction {

    private Carney carney;

    public Kick(Carney _carney) {
        carney = _carney;
    }

    public boolean execute() {
        carney.fire();
        return true;
    }

}
