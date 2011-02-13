package com.bhrobotics.morlib;

// TODO: Test me.
public class ControlListener implements Listener {
    private EventEmitter process = Reactor.getProcess();
    
    protected JoystickFilter joystickFilter = new JoystickFilter();
    protected DSInputFilter dsInputFilter = new DSInputFilter();
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name == "start") {
            start();
        } else if (name == "stop") {
            stop();
        } else if (name == "startAutonomous") {
            startAutonomous();
        } else if (name == "stopAutonomous") {
            stopAutonomous();
        } else if (name == "startOperatorControl") {
            process.bind("newDataAvailable", joystickFilter);
            process.bind("newDataAvailable", dsInputFilter);
            startOperatorControl();
        } else if (name == "stopOperatorControl") {
            process.unbind("newDataAvailable", joystickFilter);
            process.unbind("newDataAvailable", dsInputFilter);
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