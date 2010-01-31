package com.bhrobotics.widowmaker.model;

public class Carney {

    private static final double WINCH_SPEED = 1.0;
    private static final double CLUTCH_ENGAGED_POSITION = 1.0;
    private static final double CLUTCH_RELEASED_POSITION = 0.0;

    private static Carney instance = new Carney();

    private double winch;
    private boolean brake;
    private double clutch;

    public Carney() { release(); }

    public static Carney getInstance() { return Carney.instance; }
    public double getWinch() { return winch; }
    public boolean getBrake() { return brake; }
    public double getClutch() { return clutch; }

    // We're not going engage the brake during an emergency stop, as engaging a
    // brake while Carney is kicking could chew up some gears.
    public void emergencyStop() { stopWinch(); }

    public void windUp() {
        windWinch();
        releaseBrake();
        engageClutch();
    }

    public void hold() {
        stopWinch();
        engageBrake();
        engageClutch();
    }

    public void release() {
        stopWinch();
        releaseBrake();
        releaseClutch();
    }

    private void windWinch() { winch = WINCH_SPEED; }
    private void stopWinch() { winch = 0; }
    private void engageBrake() { brake = true; }
    private void releaseBrake() { brake = false; }
    private void engageClutch() { clutch = CLUTCH_ENGAGED_POSITION; }
    private void releaseClutch() { clutch = CLUTCH_RELEASED_POSITION; }
}