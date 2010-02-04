package com.bhrobotics.widowmaker.models;

import edu.wpi.first.wpilibj.DigitalInput;

// Carney, the former kicker of the Saints, is the kicking mechanism on the
// robot. It's a very simple pseudo-FSM.
public class Carney {

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

    public void start() {
        stopWinch();
        releaseBrake();
        releaseClutch();
    }

    public void stop() { stopWinch(); }

    public void wind() {
        windWinch();
        releaseBrake();
        engageClutch();
    }

    public void hold() {
        stopWinch();
        engageBrake();
        engageClutch();
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

    public boolean getTopLimit() { return topLimit.get(); }
    public boolean getBottomLimit() { return bottomLimit.get(); }
}