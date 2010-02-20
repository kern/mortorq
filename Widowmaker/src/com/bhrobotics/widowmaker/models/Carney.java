package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;
import edu.wpi.first.wpilibj.Timer;

// Carney, the former kicker of the Saints, is the kicking mechanism on the
// robot. It's a very simple pseudo-FSM.
public class Carney implements Model {

    //**************************************************************************
    // State
    //**************************************************************************

    private boolean fireTwo = false;
    private boolean fireFour = false;

    private boolean topLimit = false;
    private boolean bottomLimit = false;

    private Timer timer = new Timer();
    private static final double DELAY = 2.0;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public void start() {}
    public void stop() {}

    public void fireTwo() {
        if(topLimit) {
            fireTwo = true;
            fireFour = false;
        }
    }

    public void fireFour() {
        if(topLimit) {
            fireTwo = false;
            fireFour = true;
        }
    }

    public void fireSix() {
        if(topLimit) {
            fireTwo = true;
            fireFour = true;
        }
    }

    public void retract() {
        if(bottomLimit && timer.get() > DELAY) {
            
            // Reset the timer
            if(timer.get() != 0) {
                timer.stop();
                timer.reset();
            }

            fireTwo = false;
            fireFour = false;
        }
    }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public boolean getFireTwo() { return fireTwo; }
    public boolean getFireFour() { return fireFour; }
    public boolean getRetract() { return !(fireTwo || fireFour); }
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