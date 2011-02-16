package com.bhrobotics.morlib;

public class ControlListener implements Listener {
    private EventEmitter process = Reactor.getProcess();
    
    protected JoystickFilter joystickFilter = new JoystickFilter();
    protected DriverStationFilter dsFilter = new DriverStationFilter();
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("start")) {
            start();
        } else if (name.equals("stop")) {
            stop();
        } else if (name.equals("startAutonomous")) {
            startAutonomous();
        } else if (name.equals("stopAutonomous")) {
            stopAutonomous();
        } else if (name.equals("startOperatorControl")) {
            process.bind("newDataAvailable", joystickFilter);
            process.bind("newDataAvailable", dsFilter);
            startOperatorControl();
        } else if (name.equals("stopOperatorControl")) {
            process.unbind("newDataAvailable", joystickFilter);
            process.unbind("newDataAvailable", dsFilter);
            stopOperatorControl();
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public void start() {}
    public void stop() {}
    public void startAutonomous() {}
    public void stopAutonomous() {}
    public void startOperatorControl() {}
    public void stopOperatorControl() {}
}