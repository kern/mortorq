package com.bhrobotics.widowmaker.model.carney;

// Carney's kicked state.
public class Released implements CarneyState {
    
    private Carney carney;
    
    public Released(Carney _carney) {
        carney = _carney;

        carney.stopWinch();
        carney.releaseBrake();
        carney.releaseClutch();
    }
    
    // If I stop the robot while Carney is released, I want the robot to stop
    // the motor, release the clutch, but not engage the brake. This is known as
    // a dead emergency stop.
    public void emergencyStop() {
        carney.setState(new DeadStop(carney));
    }
    
    // Immediately start winding up once we hit the bottom limit.
    public void bottomLimitHit() {
        carney.setState(new WindUp(carney));
    }

    // Firing without any sort of tension in the bands is a no-no. Let's prevent
    // it from ever happening.
    public void exitEmergencyStop() {}
    public void fire() {}
    public void topLimitHit() {}
}