package com.bhrobotics.widowmaker.model.carney;

public class DeadStop implements CarneyState {

    private Carney carney;

    public DeadStop(Carney _carney) {
        carney = _carney;

        carney.stopWinch();
        carney.releaseBrake();
        carney.releaseClutch();
    }

    // After an emergency stop, go back to the Released state.
    public void exitEmergencyStop() {
        carney.setState(new Released(carney));
    }

    // Emergency stopped stuff can't move.
    public void emergencyStop() {}
    public void fire() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}