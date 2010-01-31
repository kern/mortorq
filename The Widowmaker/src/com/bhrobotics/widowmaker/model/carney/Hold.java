package com.bhrobotics.widowmaker.model.carney;

// State of Carney with the highest potential energy; fully wound winch.
public class Hold implements CarneyState {

    private Carney carney;

    public Hold(Carney _carney) {
        carney = _carney;

        carney.stopWinch();
        carney.engageBrake();
        carney.engageClutch();
    }
    
    // If I stop the robot while it's holding, I want the robot to stop the
    // winch to be relaxed, but the brake and clutch to be engaged. This is also
    // known as a tense stop.
    public void emergencyStop() {
        carney.setState(new TenseStop(carney));
    }

    // FFFFFIIIIIRRRRREEEEE!!!!!
    public void fire() {
        carney.setState(new Released(carney));
    }

    // When the top limit is hit, we want to continue to hold the tension.
    public void exitEmergencyStop() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}