package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;

// Carney, the former kicker of the Saints, is the kicking mechanism on the
// robot. It's a very simple pseudo-FSM.
public class Carney implements Model {

    //**************************************************************************
    // State
    //**************************************************************************

    private static final int FIRED = 0;
    private static final int RETRACTED = 1;

    private int state = FIRED;

    private boolean compressor;

    private boolean topLimit;
    private boolean bottomLimit;
    
    //**************************************************************************
    // Interface
    //**************************************************************************

    public void start() {}
    public void stop() {}

    public void fire() {
        compressor = true;
    }

    public void retract() {
        compressor = false;
    }

    public void checkLimits() {
        if(state == FIRED && bottomLimit) {
            //wind();
        }

        if(state == RETRACTED && topLimit) {
            //hold();
        }
    }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public boolean getCompressor() { return compressor; }

    //**************************************************************************
    // Setters
    //**************************************************************************

    public void setTopLimit(boolean _topLimit) {
        topLimit = _topLimit;
    }

    public void setBottomLimit(boolean _bottomLimit) {
        bottomLimit = _bottomLimit;
    }
}