package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.DashboardListener;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.Reactor;

class MorTorqControlListener extends ControlListener {
    private LineTrackerFilter lineTrackerFilter       = new LineTrackerFilter();
    private MorTorqTouchPanelFilter panelFilter       = new MorTorqTouchPanelFilter();
    private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    private CompressorListener compressorListener     = new CompressorListener();
    private MastListener mastListener                 = new MastListener();
    private EventEmitter endGameEmitter               = new EventEmitter();
    
    public void start() {
        process.bind("tick", mastListener);
        process.bind("tick", compressorListener);
    }
    
    public void startAutonomous() {
        compressorListener.auto();
        Wrist.getInstance().raise();
        Elbow.getInstance().raise();
        Minibot.getInstance().retract();
        
        process.bind("tick", lineTrackerFilter);
        lineTrackerFilter.bind("all", mecanumDriveListener);
        lineTrackerFilter.bind("all", mastListener);
    }
    
    public void stopAutonomous() {
        process.unbind("tick", lineTrackerFilter);
        lineTrackerFilter.unbindAll();
    }
    
    public void startOperatorControl() {
        joystickFilter.bind("changeJoystick1", panelFilter);
        dsFilter.bind("all", panelFilter);
        dsFilter.update(false);
        
        panelFilter.bind("all", mecanumDriveListener);
        panelFilter.bind("all", compressorListener);
        panelFilter.bind("all", mastListener);
        
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
        panelFilter.unbindAll();
        
        endGameEmitter.unbindAll();
        endGameEmitter.cancelAll();
    }
}