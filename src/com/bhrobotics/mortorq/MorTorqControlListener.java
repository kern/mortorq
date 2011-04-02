package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.DashboardListener;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.Reactor;

public class MorTorqControlListener extends ControlListener {
    private LineTrackerListener lineTrackerListener = new LineTrackerListener();
    private MorTorqTouchPanelListener panelListener = new MorTorqTouchPanelListener();
    private EventEmitter endGameEmitter             = new EventEmitter();
    
    public void start() {
        process.bind("tick", new Listener() {
            public void handle(Event event) {
                Compressor.getInstance().update();
                Mast.getInstance().update();
            }
        });
    }
    
    public void startAutonomous() {
        Compressor.getInstance().auto();
        Wrist.getInstance().raise();
        Elbow.getInstance().raise();
        Minibot.getInstance().retract();
        
        process.bind("tick", lineTrackerListener);
        lineTrackerListener.setAtPeg(false);
        lineTrackerListener.stop();
    }
    
    public void stopAutonomous() {
        process.unbind("tick", lineTrackerListener);
    }
    
    public void startOperatorControl() {
        joystickFilter.bind("changeJoystick1", panelListener);
        dsFilter.bind("all", panelListener);
        dsFilter.update(false);
        
        endGameEmitter.bind("startEndGame", new Listener() {
            public void handle(Event event) {
                Minibot.getInstance().setEndGame(true);
            }
        });
        
        endGameEmitter.schedule("startEndGame", 110);
    }
    
    public void stopOperatorControl() {
        joystickFilter.unbindAll();
        dsFilter.unbindAll();
        
        endGameEmitter.unbindAll();
        endGameEmitter.cancelAll();
    }
}