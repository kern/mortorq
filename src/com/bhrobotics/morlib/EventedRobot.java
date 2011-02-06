package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;

// TODO: Test me.
public class EventedRobot extends SimpleRobot {
    protected Reactor reactor = Reactor.getInstance();
    protected EventEmitter process = reactor.getProcess();
    
    public EventedRobot() {
        reactor.start();
    }
    
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
        reactor.startTicking();
        process.trigger("startAutonomous");
        
        while(isAutonomous() && isSystemActive()) {
            getWatchdog().feed();
        }
        
        process.trigger("stopAutonomous");
        reactor.stopTicking();
        System.out.println("[morlib] Autonomous stopped.");
    }
    
    public void operatorControl() {
        System.out.println("[morlib] Operator control started.");
        reactor.startTicking();
        process.trigger("startOperatorControl");
        
        while(isOperatorControl() && isSystemActive()) {
            if(isNewDataAvailable()) {
                process.trigger("newDataAvailable");
            }
            
            getWatchdog().feed();
        }
        
        process.trigger("stopOperatorControl");
        reactor.stopTicking();
        System.out.println("[morlib] Operator control stopped.");
    }
    
    protected void disabled() {
        System.out.println("[morlib] Disabled started.");
        while(isDisabled()) {}
        System.out.println("[morlib] Disabled stopped.");
    }
}