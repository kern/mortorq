package com.bhrobotics.morlib;

public abstract class Controller {

    protected static final int STOPPED = 0;
    protected static final int RUNNING = 1;

    private int state = STOPPED;

    protected OperatorInterface oi;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public void init() {}
    public void shutdown() {}

    public boolean isStopped() { return false; }

    public void start() {}
    public void newData() {}
    public void running() {}

    public void stop() {}
    public void stopped() {}

    //**************************************************************************
    /// Automation
    //**************************************************************************

    public Controller(OperatorInterface _oi) { oi = _oi; }

    public void refresh() {
        boolean running = state == RUNNING;

        if(oi.getNewData()) {
            running = !isStopped();
        }
        
        if(running) {
            if(state != RUNNING) {
                state = RUNNING;
                start();
            }

            if(oi.getNewData()) { newData(); }

            running();
        }else{
            if(state != STOPPED) {
                state = STOPPED;
                stop();
            }

            stopped();
        }

        oi.setNewData(false);
    }
}