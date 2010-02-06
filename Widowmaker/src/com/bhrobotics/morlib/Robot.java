package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

public abstract class Robot extends SimpleRobot {

    protected Controller autoController;
    protected Controller teleopController;

    public void robotMain() {
        while(true) {

            // Wait for robot to be enabled.
            while(isDisabled()) {
                state = DISABLED;
                Timer.delay(.01);
            }

            // Now enabled - check if we should run Autonomous code
            if(isAutonomous()) {
                autoController.init();
                while(isAutonomous() && !isDisabled()) {
                    getWatchdog().feed();
                    autoController.refresh();
                }
                autoController.shutdown();
            }else{
                teleopController.init();
                while(isOperatorControl() && !isDisabled()) {
                    getWatchdog().feed();
                    teleopController.refresh();
                }
                teleopController.shutdown();
            }
        }
    }
}