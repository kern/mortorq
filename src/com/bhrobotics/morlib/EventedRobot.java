package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;

public class EventedRobot extends SimpleRobot {
    protected Reactor reactor = Reactor.getInstance();
    protected EventEmitter process = reactor.getProcess();
    
    public EventedRobot() {
        reactor.start();
    }
    
    public void addControlListener(ControlListener listener) {
        process.addListener("start", listener);
        process.addListener("stop", listener);
        process.addListener("startAutonomous", listener);
        process.addListener("stopAutonomous", listener);
        process.addListener("startOperatorControl", listener);
        process.addListener("stopOperatorControl", listener);
    }
    
    protected void robotInit() {
        System.out.println("[morlib] Robot started.");
    }
    
    public void autonomous() {
        System.out.println("[morlib] Autonomous started.");
        reactor.startTicking();
        process.emit("startAutonomous");
        
        while(isAutonomous() && isSystemActive()) {
            getWatchdog().feed();
        }
        
        process.emit("stopAutonomous");
        reactor.stopTicking();
        System.out.println("[morlib] Autonomous stopped.");
    }
    
    public void operatorControl() {
        System.out.println("[morlib] Operator control started.");
        reactor.startTicking();
        process.emit("startOperatorControl");
        
        while(isOperatorControl() && isSystemActive()) {
            if(isNewDataAvailable()) {
                process.emit("newDataAvailable");
            }
            
            getWatchdog().feed();
        }
        
        process.emit("stopOperatorControl");
        reactor.stopTicking();
        System.out.println("[morlib] Operator control stopped.");
    }
    
    protected void disabled() {
        System.out.println("[morlib] Disabled started.");
        while(isDisabled()) {}
        System.out.println("[morlib] Disabled stopped.");
    }
}