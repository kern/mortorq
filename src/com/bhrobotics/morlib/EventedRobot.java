package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;

public class EventedRobot extends SimpleRobot {
    protected EventEmitter process = Reactor.getProcess();
    
    public void addControlListener(ControlListener listener) {
        process.bind("start", listener);
        process.bind("stop", listener);
        process.bind("startAutonomous", listener);
        process.bind("stopAutonomous", listener);
        process.bind("startOperatorControl", listener);
        process.bind("stopOperatorControl", listener);
    }
    
    protected void robotInit() {
        System.out.println("[morlib] Robot started.");
    }
    
    public void autonomous() {
        System.out.println("[morlib] Autonomous started.");
        Reactor.startTicking();
        process.trigger("startAutonomous");
        
        while(isAutonomous() && isSystemActive()) {
            getWatchdog().feed();
        }
        
        process.trigger("stopAutonomous");
        Reactor.stopTicking();
        System.out.println("[morlib] Autonomous stopped.");
    }
    
    public void operatorControl() {
        System.out.println("[morlib] Operator control started.");
        Reactor.startTicking();
        process.trigger("startOperatorControl");
        
        while(isOperatorControl() && isSystemActive()) {
            if(isNewDataAvailable()) {
                process.trigger("newDataAvailable");
            }
            
            getWatchdog().feed();
        }
        
        process.trigger("stopOperatorControl");
        Reactor.stopTicking();
        System.out.println("[morlib] Operator control stopped.");
    }
    
    protected void disabled() {
        System.out.println("[morlib] Disabled started.");
        while(isDisabled()) {}
        System.out.println("[morlib] Disabled stopped.");
    }
}