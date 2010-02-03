package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.SimpleRobot;

// The VM is configured to automatically run this class, and to call the
// functions corresponding to each mode, as described in the IterativeRobot
// documentation.
public class Widowmaker extends SimpleRobot {

    private AutoController autoController;
    private TeleopController teleopController;

    public void robotInit() {
        autoController = new AutoController();
        teleopController = new TeleopController();
    }

    public void autonomous() {
        while(this.isAutonomous()) {
            getWatchdog().feed();
            autoController.refresh();
        }
    }

    public void operatorControl() {
        while(this.isOperatorControl()) {
            getWatchdog().feed();
            teleopController.refresh();
        }
    }
}