package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.SimpleRobot;

public class EventedRobot extends SimpleRobot {
    private Reactor reactor = new Reactor();
    private Listener controlListener;
    
    public EventedRobot() {
        getProcess().addListener("start", controlListener);
        getProcess().addListener("stop", controlListener);
        getProcess().addListener("startAutonomous", controlListener);
        getProcess().addListener("stopAutonomous", controlListener);
        getProcess().addListener("startOperatorControl", controlListener);
        getProcess().addListener("stopOperatorControl", controlListener);
    }
    
    public void autonomous() {
        getProcess().emit("startAutonomous");
        reactor.startTicking();
        
        while(isAutonomous() && isSystemActive()) {
            getWatchdog().feed();
        }
        
        getProcess().emit("stopAutonomous");
        reactor.stopTicking();
    }
    
    public void operatorControl() {
        getProcess().emit("startOperatorControl");
        reactor.startTicking();
        
        while(isOperatorControl() && isSystemActive()) {
            getWatchdog().feed();
        }
        
        getProcess().emit("stopOperatorControl");
        reactor.stopTicking();
    }
    
    public Reactor getReactor() {
        return reactor;
    }
    
    public EventEmitter getProcess() {
        return reactor.getProcess();
    }
}