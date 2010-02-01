package com.bhrobotics.widowmaker.model.carney;

// State of Carney with a high amount of potential energy. Stopped state.
public class TenseStop implements CarneyState {

    public TenseStop() {
        Carney.stopWinch();
        Carney.engageBrake();
        Carney.engageClutch();
    }

    // After an emergency stop, go back to the Hold state.
    public void run() {
        Carney.setState(new Hold());
    }

    // Emergency stopped stuff can't move.
    public void stop() {}
    public void fire() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}