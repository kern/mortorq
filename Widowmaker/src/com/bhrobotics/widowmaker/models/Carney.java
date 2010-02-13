package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;
import edu.wpi.first.wpilibj.Timer;

// Carney, the former kicker of the Saints, is the kicking mechanism on the
// robot. It's a very simple pseudo-FSM.
public class Carney implements Model {

    //**************************************************************************
    // State
    //**************************************************************************

    private static final int FIRED = 0;
    private static final int RETRACTED = 1;

    private int state = FIRED;

    private boolean carney;

    private boolean topLimit;
    private boolean bottomLimit;

    private Timer timer = new Timer();
    private static final int DELAY = 1000000;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public void start() {}
    public void stop() {}

    public void fire() {
        if(topLimit) {
            carney = true;
        }
    }

    public void retract() {
        if(bottomLimit && timer.get() > DELAY) {
            timer.stop();
            carney = false;
        }
    }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public boolean getTopLimit() { return topLimit; }
    public boolean getBottomLimit() { return bottomLimit; }
    
    //**************************************************************************
    // Setters
    //**************************************************************************

    public void setTopLimit(boolean _topLimit) {
        topLimit = _topLimit;
    }

    public void setBottomLimit(boolean _bottomLimit) {
        if(_bottomLimit && !bottomLimit) {
            timer.reset();
            timer.start();
        }

        bottomLimit = _bottomLimit;
    }
}