package com.bhrobotics.widowmaker.model.carney;

// State of Carney with the highest potential energy; fully wound winch.
public class Hold extends CarneyState {
    
    // If I stop the robot while it's holding, I want the robot to stop the
    // winch to be relaxed, but the brake and clutch to be engaged. This is also
    // known as a tense stop.
    public void stop() {
        carney.setState(new TenseStop(carney));
    }

    // FFFFFIIIIIRRRRREEEEE!!!!!
    public void fire() {
        carney.setState(new Released(carney));
    }
}