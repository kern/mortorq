package com.bhrobotics.widowmaker.model.carney;

// Carney's kicked state.
public class Released extends CarneyState {
    
    // If I stop the robot while Carney is released, I want the robot to stop
    // the motor, release the clutch, but not engage the brake. This is known as
    // a dead emergency stop.
    public void stop() {
        carney.setState(new DeadStop(carney));
    }
    
    // Immediately start winding up once we hit the bottom limit.
    public void bottomLimitHit() {
        carney.setState(new WindUp(carney));
    }
}