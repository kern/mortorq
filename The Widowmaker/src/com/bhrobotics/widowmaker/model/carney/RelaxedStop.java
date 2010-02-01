package com.bhrobotics.widowmaker.model.carney;

// State of Carney with a small amount of potential energy. Emergency stopped.
public class RelaxedStop implements CarneyState {

    public RelaxedStop() {
        Carney.stopWinch();
        Carney.releaseBrake();
        Carney.engageClutch();
    }

    // After an emergency stop, go back to the WindUp state.
    public void run() {
        Carney.setState(new WindUp());
    }

    // Emergency stopped stuff can't move.
    public void stop() {}
    public void fire() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}