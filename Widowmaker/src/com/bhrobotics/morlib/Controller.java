package com.bhrobotics.morlib;

public abstract class Controller extends Thread {

    private static final int DISABLED = 0;
    private static final int STOPPED = 1;
    private static final int ACTIVE = 2;

    private int state = DISABLED;

    //**************************************************************************
    // State-keeping
    //**************************************************************************

    public void disable() { state = DISABLED; }
    public void stop() { state = STOPPED; }
    public void activate() { state = ACTIVE; }

    public boolean isEnabled() { return state != DISABLED; }
    public boolean isDisabled() { return !isEnabled(); }
    public boolean isActive() { return state == ACTIVE; }
    public boolean isStopped() { return state == STOPPED; }

    //**************************************************************************
    // Multi-threading
    //**************************************************************************

    public void run() {
        while(isEnabled()) {
            if(isActive()) {
                if() {

                }else{

                }
            }else{
                whenStopped();
            }
        }

    }
}