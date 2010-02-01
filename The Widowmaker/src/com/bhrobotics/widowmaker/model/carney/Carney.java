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
    private static CarneyState state = new Released();

    // Digital inputs
    private static DigitalInput topLimit = new DigitalInput(TOP_LIMIT);
    private static DigitalInput bottomLimit = new DigitalInput(BOTTOM_LIMIT);

    // Outputs
    private static double winch;
    private static boolean brake;
    private static double clutch;
    
    // FSM stuff
    public static void setState(CarneyState _state) { state = _state; }
    public static void stoppedInit() { state.stop(); }
    public static void runningInit() { state.run(); }
    public static void fire() { state.fire(); }

    // Outputs
    public static double getWinch() { return winch; }
    public static boolean getBrake() { return brake; }
    public static double getClutch() { return clutch; }
    
    // Limit switches
    public static void checkLimits() {
        if(topLimit.get()) { state.topLimitHit(); }
        if(bottomLimit.get()) { state.bottomLimitHit(); }
    }

    // Output controls
    public static void windWinch() { winch = WINCH_SPEED; }
    public static void stopWinch() { winch = 0; }
    public static void engageBrake() { brake = true; }
    public static void releaseBrake() { brake = false; }
    public static void engageClutch() { clutch = CLUTCH_ENGAGED_POSITION; }
    public static void releaseClutch() { clutch = CLUTCH_RELEASED_POSITION; }
}