package com.bhrobotics.morlib;

public abstract class Controller {

    private static final int STOPPED = 0;
    private static final int RUNNING = 1;

    private int state = STOPPED;

    // Called by Widowmaker to refresh the robot's models and views. Essentially
    // it's the method that makes the robot do stuff.
    void refresh() {
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
    boolean isActive() { return true; }

    // Called once when starting or stopping the controller.
    void start() {}
    void stop() {}

    // Run continuously while the robot is running or stopped
    void running() {}
    void stopped() {}

    // Renders views
    void render() {}
}