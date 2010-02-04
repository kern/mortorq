package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;

public abstract class Robot extends SimpleRobot {
    
    protected Controller autoController;
    protected Controller teleopController;

    public void autonomous() {
        while(isAutonomous()) {
            getWatchdog().feed();
            autoController.refresh();
        }
    }

    public void operatorControl() {
        while(isAutonomous()) {
            getWatchdog().feed();
            teleopController.refresh();
        }
    }
}