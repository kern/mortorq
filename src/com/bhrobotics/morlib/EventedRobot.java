package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;

public class EventedRobot extends SimpleRobot {
    protected EventEmitter process = Reactor.getProcess();
    protected JoystickFilter joystickFilter = new JoystickFilter();
    protected DriverStationFilter dsFilter = new DriverStationFilter();
    
    protected void robotInit() {
        System.out.println("[morlib] Robot started.");
        start();
    }
    
    public void autonomous() {
        System.out.println("[morlib] Autonomous started.");
        Reactor.startTicking();
        startAutonomous();
        
        while(isAutonomous() && isSystemActive()) {
            getWatchdog().feed();
        }
        
        stopAutonomous();
        Reactor.stopTicking();
        System.out.println("[morlib] Autonomous stopped.");
    }
    
    public void operatorControl() {
        System.out.println("[morlib] Operator control started.");
        process.bind("newDataAvailable", joystickFilter);
        process.bind("newDataAvailable", dsFilter);
        Reactor.startTicking();
        
        startOperatorControl();
        
        while(isOperatorControl() && isSystemActive()) {
            if(isNewDataAvailable()) {
                process.trigger("newDataAvailable");
            }
            
            getWatchdog().feed();
        }
        
        stopOperatorControl();
        
        Reactor.stopTicking();
        process.unbind("newDataAvailable", joystickFilter);
        process.unbind("newDataAvailable", dsFilter);
        System.out.println("[morlib] Operator control stopped.");
    }
    
    protected void disabled() {
        System.out.println("[morlib] Disabled started.");
        while(isDisabled()) {}
        System.out.println("[morlib] Disabled stopped.");
    }
    
    public void start() {}
    public void startAutonomous() {}
    public void stopAutonomous() {}
    public void startOperatorControl() {}
    public void stopOperatorControl() {}
}