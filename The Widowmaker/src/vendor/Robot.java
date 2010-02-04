package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;
import

public class Robot extends SimpleRobot {

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