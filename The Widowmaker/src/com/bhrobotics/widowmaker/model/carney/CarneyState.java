package com.bhrobotics.widowmaker.model.carney;

// For the FSM that is used to control Carney
public interface CarneyState {
    public void stop();
    public void run();

    public void fire();
    public void topLimitHit();
    public void bottomLimitHit();
}