package com.bhrobotics.widowmaker.model.carney;

// Winding up state of Carney.
public class WindUp implements CarneyState {

    private Carney carney;

    public WindUp(Carney _carney) {
        carney = _carney;

        carney.windWinch();
        carney.releaseBrake();
        carney.engageClutch();
    }

    // When the top limit is hit, we want to hold the tension.
    public void topLimitHit() {
        carney.setState(new Hold(carney));
    }

    // If I stop the robot while winding up, I want the robot to stop the motor,
    // but not engage the brake. This is a relaxed emergency stop.
    public void emergencyStop() {
        carney.setState(new RelaxedStop(carney));
    }

    // Firing while building tension in the bands is a no-no. Let's prevent it
    // from ever happening.
    public void fire() {}
    public void bottomLimitHit() {}
    public void exitEmergencyStop() {}
}