package com.bhrobotics.widowmaker.model.carney;

// Winding up state of Carney.
public class WindUp implements CarneyState {

    public WindUp() {
        Carney.windWinch();
        Carney.releaseBrake();
        Carney.engageClutch();
    }
    
    // If I stop the robot while winding up, I want the robot to stop the motor,
    // but not engage the brake. This is a relaxed emergency stop.
    public void stop() {
        Carney.setState(new RelaxedStop());
    }

    // When the top limit is hit, we want to hold the tension.
    public void topLimitHit() {
        Carney.setState(new Hold());
    }

    // Firing while building tension in the bands is a no-no. Let's prevent it
    // from ever happening.
    public void run() {}
    public void fire() {}
    public void bottomLimitHit() {}
}