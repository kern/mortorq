package com.bhrobotics.widowmaker.model.carney;

public interface CarneyState {
    public void emergencyStop();
    public void exitEmergencyStop();
    public void fire();
    public void topLimitHit();
    public void bottomLimitHit();
}