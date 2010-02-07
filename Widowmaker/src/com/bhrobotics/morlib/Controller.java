package com.bhrobotics.morlib;

public abstract class Controller {

    protected static final int STOPPED = 0;
    protected static final int RUNNING = 1;

    private int state = STOPPED;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public void init() {}
    public void shutdown() {}

    public boolean isStopped(OperatorInterface oi) { return false; }

    public void start(OperatorInterface oi) {}
    public void running(OperatorInterface oi) {}

    public void stop() {}
    public void stopped() {}

    //**************************************************************************
    /// Automation
    //**************************************************************************

    public void refresh(OperatorInterface oi) {
        if(!isStopped(oi)) {
            if(state != RUNNING) {
                state = RUNNING;
                start(oi);
            }
            running(oi);
        }else{
            if(state != STOPPED) {
                state = STOPPED;
                stop();
            }
            stopped();
        }
    }
}