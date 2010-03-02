package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;

public abstract class Robot extends RobotBase {

    // Modes the robot can be in:
    // Disabled: Completely disabled by the FSM. Nothing wroks pretty much.
    // Autonomous: Robot does not receive any human input.
    // Teleop: Normal robot mode. Receives data fromt the DS normally.
    // Stopped: Sets all robot settings to defaults, a soft e-stop.
    public static class Mode {
        public final int mode;
        private Mode(int _mode) { mode = _mode; }

        private static final int DISABLED = 0;
        private static final int AUTONOMOUS = 1;
        private static final int TELEOP = 2;
        private static final int STOPPED = 3;

        public static final Mode disabled = new Mode(DISABLED);
        public static final Mode autonomous = new Mode(AUTONOMOUS);
        public static final Mode teleop = new Mode(TELEOP);
        public static final Mode stopped = new Mode(STOPPED);
    }

    // Spawns threads for the different modes. Each thread is a controller which
    // has complete control over the robot until stopped.
    public void startCompetition() {
        while(true) {

            // Nothing to do while disabled.
            while(isDisabled()) { Timer.delay(.01); }

            if(isAutonomous())
            // Now enabled - check if we should run Autonomous code
            if(isAutonomous()) {
                autoController.init();
                while(isAutonomous() && !isDisabled()) {
                    getWatchdog().feed();
                    update();
                    autoController.refresh();
                    render();
                }
                autoController.shutdown();
            }else{
                teleopController.init();
                while(isOperatorControl() && !isDisabled()) {
                    getWatchdog().feed();
                    update();
                    
                    // Check for new data
                    if(isNewDataAvailable()) {
                        oi.refresh();
                    }

                    teleopController.refresh();
                    render();
                }
                teleopController.shutdown();
            }
        }
    }

    public Mode getMode() {
        if(isDisabled()) { return Mode.disabled; }
        if(isAutonomous()) { return Mode.autonomous; }

        oi.refresh();
        if(oi.isStopped()) { return Mode.stopped; }

        return Mode.teleop;
    }
}