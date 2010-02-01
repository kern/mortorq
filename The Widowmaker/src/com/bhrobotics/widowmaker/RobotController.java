package com.bhrobotics.widowmaker;

// Abstract base class for controllers. Interprets the input from both the robot
// IO and the operator interface and sends it to the models, such as the drive
// train and manipulator. The data is then sent to the corresponding view.
abstract class RobotController {

    private static final int STOPPED = 0;
    private static final int RUNNING = 1;

    private int state = RUNNING;

    void init() {}

    // Called by Widowmaker to run the robot. If the
    void run() {
        if(isStopped()) {
            if(state != STOPPED) { stoppedInit(); }
            stopped();
        }else{
            if(state != RUNNING) { runningInit(); }
            running();
        }

        render();
    }
    
    // Called at various times in the robots execution. Must be implemented by
    // all controllers. Not called directly from Widowmaker!
    abstract boolean isStopped();

    void stoppedInit() {}
    abstract void stopped(); // Run continuously while the robot is stopped

    void runningInit() {}
    abstract void running(); // Run continuously while the robot is running

    abstract void render(); // Renders views
}