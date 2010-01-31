package com.bhrobotics.widowmaker.model.carney;

// For the FSM that is used to control Carney
public interface CarneyState {
    public void emergencyStop();
    public void exitEmergencyStop();
    public void fire();
    public void topLimitHit();
    public void bottomLimitHit();
}