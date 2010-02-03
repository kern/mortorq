package com.bhrobotics.widowmaker.model.carney;

// Winding up state of Carney.
public class WindUp extends CarneyState {
    
    // If I stop the robot while winding up, I want the robot to stop the motor,
    // but not engage the brake. This is a relaxed emergency stop.
    public void stop() {
        carney.setState(new RelaxedStop(carney));
    }

    // When the top limit is hit, we want to hold the tension.
    public void topLimitHit() {
        carney.setState(new Hold(carney));
    }
}