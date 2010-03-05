package com.bhrobotics.morlib;

import com.bhrobotics.widowmaker.TouchInterface;
import edu.wpi.first.wpilibj.SimpleRobot;
import java.util.Vector;
import java.util.Enumeration;

public abstract class Robot extends SimpleRobot {

    protected Controller autoController;
    protected Controller teleopController;
    protected TouchInterface oi;

    protected Vector views = new Vector();

    public void autonomous() {
        autoController.start();
        while(isAutonomous() && !isDisabled()) {
            getWatchdog().feed();

            update();
            autoController.run();
            render();
        }
    }
    
    public void operatorControl() {
        teleopController.start();
        while(isOperatorControl() && !isDisabled()) {
            getWatchdog().feed();
            
            // Check for new data
            boolean newData = isNewDataAvailable();
            if(newData) { oi.refresh(); }
            
            update();
            if(!oi.getStopped()) {
                if(newData) { teleopController.newData(); }
                teleopController.run();
            }else{
                teleopController.stop();
            }
            render();
        }
    }

    protected void addView(View view) {
        views.addElement(view);
    }

    private void update() {
        Enumeration en = views.elements();
        while(en.hasMoreElements()) {
            View view = (View) en.nextElement();
            view.update();
        }
    }

    private void render() {
        Enumeration en = views.elements();
        while(en.hasMoreElements()) {
            View view = (View) en.nextElement();
            view.render();
        }
    }
}