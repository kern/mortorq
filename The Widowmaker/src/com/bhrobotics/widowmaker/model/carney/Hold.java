package com.bhrobotics.widowmaker.model.carney;

// State of Carney with the highest potential energy; fully wound winch.
public class Hold implements CarneyState {

    public Hold() {
        Carney.stopWinch();
        Carney.engageBrake();
        Carney.engageClutch();
    }
    
    // If I stop the robot while it's holding, I want the robot to stop the
    // winch to be relaxed, but the brake and clutch to be engaged. This is also
    // known as a tense stop.
    public void stop() {
        Carney.setState(new TenseStop());
    }

    // FFFFFIIIIIRRRRREEEEE!!!!!
    public void fire() {
        Carney.setState(new Released());
    }

    // When the top limit is hit, we want to continue to hold the tension.
    public void run() {}
    public void topLimitHit() {}
    public void bottomLimitHit() {}
}