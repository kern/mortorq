package com.bhrobotics.widowmaker.models;

import edu.wpi.first.wpilibj.DigitalInput;

// Carney, the former kicker of the Saints, is the kicking mechanism on the
// robot. It's a very simple pseudo-FSM.
public class Carney {

    //**************************************************************************
    // State
    //**************************************************************************

    private static final int FIRED = 0;
    private static final int WINDING = 1;
    private static final int HOLDING = 2;

    private int state = FIRED;

    //**************************************************************************
    // IO
    //**************************************************************************

    private static final int TOP_LIMIT = 1;
    private static final int BOTTOM_LIMIT = 2;

    private DigitalInput topLimit = new DigitalInput(TOP_LIMIT);
    private DigitalInput bottomLimit = new DigitalInput(BOTTOM_LIMIT);

    private double winch;
    private boolean brake;
    private double clutch;

    //**************************************************************************
    // Speeds and positions
    //**************************************************************************

    private static final double WINCH_SPEED = 1.0;
    private static final double CLUTCH_ENGAGED_POSITION = 1.0;
    private static final double CLUTCH_RELEASED_POSITION = 0.0;

    //**************************************************************************
    // Interface
    //**************************************************************************

    // Also the firing function.
    public void start() { fire(); }

    public void stop() { stopWinch(); }

    public void fire() {
        state = FIRED;
        stopWinch();
        releaseBrake();
        releaseClutch();
    }

    public void wind() {
        state = WINDING;
        windWinch();
        releaseBrake();
        engageClutch();
    }

    public void hold() {
        state = HOLDING;
        stopWinch();
        engageBrake();
        engageClutch();
    }

    public void checkLimits() {
        if(state == FIRED && bottomLimit.get()) {
            wind();
        }

        if(state == WINDING && topLimit.get()) {
            hold();
        }
    }

    //**************************************************************************
    // Internal
    //**************************************************************************

    private void windWinch() { winch = WINCH_SPEED; }
    private void stopWinch() { winch = 0; }
    private void engageBrake() { brake = true; }
    private void releaseBrake() { brake = false; }
    private void engageClutch() { clutch = CLUTCH_ENGAGED_POSITION; }
    private void releaseClutch() { clutch = CLUTCH_RELEASED_POSITION; }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public double getWinch() { return winch; }
    public boolean getBrake() { return brake; }
    public double getClutch() { return clutch; }
}