package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import java.util.Vector;
import java.util.Enumeration;

public abstract class Robot extends SimpleRobot {

    protected Controller autoController;
    protected Controller teleopController;
    protected OperatorInterface oi = new OperatorInterface();

    protected Vector views = new Vector();

    public void robotMain() {
        while(true) {

            // Wait for robot to be enabled.
            while(isDisabled()) { Timer.delay(.01); }

            // Now enabled - check if we should run Autonomous code
            if(isAutonomous()) {
                autoController.init();
                while(isAutonomous() && !isDisabled()) {
                    getWatchdog().feed();
                    autoController.refresh(oi);
                    render();
                }
                autoController.shutdown();
            }else{
                teleopController.init();
                while(isOperatorControl() && !isDisabled()) {
                    getWatchdog().feed();
                    teleopController.refresh(oi);
                    render();
                }
                teleopController.shutdown();
            }
        }
    }

    protected void addView(View view) {
        views.addElement(view);
    }

    private void render() {
        Enumeration en = views.elements();
        while(en.hasMoreElements()) {
            View view = (View) en.nextElement();
            view.render();
        }
    }
}