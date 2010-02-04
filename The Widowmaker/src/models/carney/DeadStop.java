package com.bhrobotics.widowmaker.model.carney;

// The ground state of the system during an emergency stop.
public class DeadStop extends CarneyState {
    
    // After an emergency stop, go back to the Released state.
    public void start() {
        carney.setState(new Released(carney));
    }
}