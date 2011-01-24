package com.bhrobotics.morlib;

import java.util.Hashtable;

public class ControlListener extends Listener {
    private JoystickListener joystickListener = new JoystickListener();
    protected EventEmitter joystickEmitter    = joystickListener.getEmitter();
    
    private DSInputListener dsInputListener = new DSInputListener();
    protected EventEmitter dsInputEmitter   = dsInputListener.getEmitter();
    
    private Hashtable eventHandlers = new Hashtable();
    
    public ControlListener() {
        eventHandlers.put("start", new StartHandler());
        eventHandlers.put("stop", new StopHandler());
        eventHandlers.put("startAutonomous", new StartAutonomousHandler());
        eventHandlers.put("stopAutonomous", new StopAutonomousHandler());
        eventHandlers.put("startOperatorControl", new StartOperatorControlHandler());
        eventHandlers.put("stopOperatorControl", new StopOperatorControlHandler());
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        EventHandler h = (EventHandler) eventHandlers.get(name);
        h.execute();
    }
    
    public void start() {}
    public void stop() {}
    public void startAutonomous() {}
    public void stopAutonomous() {}
    public void startOperatorControl() {}
    public void stopOperatorControl() {}
    
    private interface EventHandler {
        public void execute();
    }
    
    private class StartHandler implements EventHandler {
        public void execute() {
            start();
        }
    }
    
    private class StopHandler implements EventHandler {
        public void execute() {
            stop();
        }
    }
    
    private class StartAutonomousHandler implements EventHandler {
        public void execute() {
            startAutonomous();
        }
    }
    
    private class StopAutonomousHandler implements EventHandler {
        public void execute() {
            stopAutonomous();
        }
    }
    
    private class StartOperatorControlHandler implements EventHandler {
        public void execute() {
            process.bind("newDataAvailable", joystickListener);
            process.bind("newDataAvailable", dsInputListener);
            startOperatorControl();
        }
    }
    
    private class StopOperatorControlHandler implements EventHandler {
        public void execute() {
            process.unbind("newDataAvailable", joystickListener);
            process.unbind("newDataAvailable", dsInputListener);
            stopOperatorControl();
        }
    }
}