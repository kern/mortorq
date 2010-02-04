package com.bhrobotics.widowmaker.model.carney;

// For the FSM that is used to control Carney
public class CarneyState {

    protected Carney carney;

    public CarneyState(Carney _carney) {
        carney = _carney;
    }

    public void start() {};
    public void stop() {};

    public void fire() {};
    public void topLimitHit() {};
    public void bottomLimitHit() {};
}