package com.bhrobotics.widowmaker.model.carney;

import edu.wpi.first.wpilibj.DigitalInput;

// Carney, the former kicker of the Saints, is the kicking mechanism on the
// robot. This model is an FSM specifically tailored to the 2010 robot and
// should not be modified in subsequent years.
public class Carney {

    private static final int TOP_LIMIT = 1;
    private static final int BOTTOM_LIMIT = 2;

    private static final double WINCH_SPEED = 1.0;
    private static final double CLUTCH_ENGAGED_POSITION = 1.0;
    private static final double CLUTCH_RELEASED_POSITION = 0.0;

    // State
    //private static CarneyState state = new Released();

    // Digital inputs
    private static DigitalInput topLimit = new DigitalInput(TOP_LIMIT);
    private static DigitalInput bottomLimit = new DigitalInput(BOTTOM_LIMIT);

    // Outputs
    private static double winch;
    private static boolean brake;
    private static double clutch;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public void start() { state.start(); }
    public void stop() { state.stop(); }
    public void fire() { state.fire(); }

    public void setState(CarneyState _state) { state = _state; }

    public void checkLimits() {
        if(topLimit.get()) { state.topLimitHit(); }
        if(bottomLimit.get()) { state.bottomLimitHit(); }
    }

    public void windWinch() { winch = WINCH_SPEED; }
    public void stopWinch() { winch = 0; }
    public void engageBrake() { brake = true; }
    public void releaseBrake() { brake = false; }
    public void engageClutch() { clutch = CLUTCH_ENGAGED_POSITION; }
    public void releaseClutch() { clutch = CLUTCH_RELEASED_POSITION; }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public double getWinch() { return winch; }
    public boolean getBrake() { return brake; }
    public double getClutch() { return clutch; }
}