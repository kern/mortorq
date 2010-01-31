package com.bhrobotics.widowmaker;

/**
 * Abstract base class for controllers. Interprets the input from both the robot
 * IO and the operator interface and sends it to the models, such as the drive
 * train and manipulator. The data is then sent to the corresponding view.
 **/
public class RobotController {

    // Called by Widowmaker to run the continuous routine. If the robot is
    // stopped via the emergency stop button, then the emergencyStop() function
    // is instead run continuously.
    public void run_continuous() {
        if(getEmergencyStop()) {
            emergencyStop();
        }else{
            continuous();
        }

        // View rendering
        render();
    }

    // Called by Widowmaker to run the periodic routine. The emergency stop
    // function is not run periodically, but rather continuously. However, we
    // still don't want this function to run if the robot is stopped.
    public void run_periodic() {
        if(!getEmergencyStop()) {
            periodic();
        }
    }

    // Must be overridden to get the robot to move.
    protected boolean getEmergencyStop() { return true; }
    
    // Called at various times in the robots execution. Should be implemented by
    // all controllers. Not called directly from Widowmaker!
    protected void emergencyStop() {}
    protected void continuous() {}
    protected void periodic() {}

    // Runs all the views at the end of a continuous loop. This is run during
    // both an emergency stop AND a normal loop. It sends all the data to the
    // motors, serveres, solenoids, etc., all at once.
    protected void render() {}
}