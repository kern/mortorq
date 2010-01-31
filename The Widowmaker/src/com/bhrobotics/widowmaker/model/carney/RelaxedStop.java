package com.bhrobotics.widowmaker.model.carney;

public class RelaxedStop implements CarneyState {

    private Carney carney;

    public RelaxedStop(Carney _carney) {
        carney = _carney;

        carney.stopWinch();
        carney.releaseBrake();
        carney.engageClutch();
    }

    // After an emergency stop, go back to the WindUp state.
    public void exitEmergencyStop() {
        carney.setState(new WindUp(carney));
    }

    // Emergency stopped stuff can't move.
    public void emergencyStop() {}
    public void fire() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}