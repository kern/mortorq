package com.bhrobotics.widowmaker;

// Abstract base class for controllers. Interprets the input from both the robot
// IO and the operator interface and sends it to the models, such as the drive
// train and manipulator. The data is then sent to the corresponding view.
class RobotController {

    private static final int STOPPED = 0;
    private static final int RUNNING = 1;

    private int state = STOPPED;

    // Called by Widowmaker to refresh the robot's models and views.
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