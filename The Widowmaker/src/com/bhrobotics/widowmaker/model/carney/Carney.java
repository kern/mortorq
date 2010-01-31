package com.bhrobotics.widowmaker.model.carney;

import edu.wpi.first.wpilibj.DigitalInput;

public class Carney {

    private static final int TOP_LIMIT = 1;
    private static final int BOTTOM_LIMIT = 2;

    private static final double WINCH_SPEED = 1.0;
    private static final double CLUTCH_ENGAGED_POSITION = 1.0;
    private static final double CLUTCH_RELEASED_POSITION = 0.0;

    // State-keeping
    private static Carney instance = new Carney();
    private CarneyState state;

    // Digital inputs
    private DigitalInput topLimit;
    private DigitalInput bottomLimit;

    // Outputs
    private double winch;
    private boolean brake;
    private double clutch;

    public Carney() {
        state = new Released(this);

        topLimit = new DigitalInput(TOP_LIMIT);
        bottomLimit = new DigitalInput(BOTTOM_LIMIT);
    }
    
    public static Carney getInstance() { return Carney.instance; }

    // FSM stuff
    public void setState(CarneyState _state) { state = _state; }
    public void fire() { state.fire(); }
    public void emergencyStop() { state.emergencyStop(); }
    public void exitEmergencyStop() { state.exitEmergencyStop(); }

    // Outputs
    public double getWinch() { return winch; }
    public boolean getBrake() { return brake; }
    public double getClutch() { return clutch; }
    
    // Limit switches
    public void checkLimits() {
        if(topLimit.get()) { state.topLimitHit(); }
        if(bottomLimit.get()) { state.bottomLimitHit(); }
    }

    // Output controls
    public void windWinch() { winch = WINCH_SPEED; }
    public void stopWinch() { winch = 0; }
    public void engageBrake() { brake = true; }
    public void releaseBrake() { brake = false; }
    public void engageClutch() { clutch = CLUTCH_ENGAGED_POSITION; }
    public void releaseClutch() { clutch = CLUTCH_RELEASED_POSITION; }
}