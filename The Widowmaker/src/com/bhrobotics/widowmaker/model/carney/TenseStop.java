package com.bhrobotics.widowmaker.model.carney;

// State of Carney with a high amount of potential energy. Stopped state.
public class TenseStop implements CarneyState {

    private Carney carney;

    public TenseStop(Carney _carney) {
        carney = _carney;

        carney.stopWinch();
        carney.engageBrake();
        carney.engageClutch();
    }

    // After an emergency stop, go back to the Hold state.
    public void exitEmergencyStop() {
        carney.setState(new Hold(carney));
    }

    // Emergency stopped stuff can't move.
    public void emergencyStop() {}
    public void fire() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}