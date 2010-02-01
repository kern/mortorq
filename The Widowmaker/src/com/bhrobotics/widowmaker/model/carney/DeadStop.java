package com.bhrobotics.widowmaker.model.carney;

// The ground state of the system during an emergency stop.
public class DeadStop implements CarneyState {

    public DeadStop() {
        Carney.stopWinch();
        Carney.releaseBrake();
        Carney.releaseClutch();
    }

    // After an emergency stop, go back to the Released state.
    public void run() {
        Carney.setState(new Released());
    }

    // Emergency stopped stuff can't move.
    public void stop() {}
    public void fire() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}