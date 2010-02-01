package com.bhrobotics.widowmaker.model.carney;

// Carney's kicked state.
public class Released implements CarneyState {
    
    public Released() {
        Carney.stopWinch();
        Carney.releaseBrake();
        Carney.releaseClutch();
    }
    
    // If I stop the robot while Carney is released, I want the robot to stop
    // the motor, release the clutch, but not engage the brake. This is known as
    // a dead emergency stop.
    public void stop() {
        Carney.setState(new DeadStop());
    }
    
    // Immediately start winding up once we hit the bottom limit.
    public void bottomLimitHit() {
        Carney.setState(new WindUp());
    }

    // Firing without any sort of tension in the bands is a no-no. Let's prevent
    // it from ever happening.
    public void run() {}
    public void fire() {}
    public void topLimitHit() {}
}