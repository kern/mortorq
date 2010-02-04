package com.bhrobotics.morlib;

public abstract class Controller {

    protected static final int STOPPED = 0;
    protected static final int RUNNING = 1;

    protected int state = STOPPED;

    // Called by Widowmaker to refresh the robot's models and views. Essentially
    // it's the method that makes the robot do stuff.
    public void refresh() {
        if(isActive()) {
            if(state != RUNNING) { start(); }
            running();
        }else{
            if(state != STOPPED) { stop(); }
            stopped();
        }

        render();
    }

    // Called to check if the robot is running or stopped.
    protected boolean isActive() { return true; }

    // Called once when starting or stopping the controller.
    protected void start() {}
    protected void stop() {}

    // Run continuously while the robot is running or stopped
    protected void running() {}
    protected void stopped() {}

    // Renders views
    protected void render() {}
}