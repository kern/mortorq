package com.bhrobotics.widowmaker.model.carney;

// State of Carney with a small amount of potential energy. Emergency stopped.
public class RelaxedStop extends CarneyState {

    // After an emergency stop, go back to the WindUp state.
    public void run() {
        carney.setState(new WindUp(carney));
    }
}