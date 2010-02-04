package com.bhrobotics.widowmaker.model.carney;

// State of Carney with a high amount of potential energy. Stopped state.
public class TenseStop extends CarneyState {

    // After an emergency stop, go back to the Hold state.
    public void run() {
        carney.setState(new Hold(carney));
    }
}