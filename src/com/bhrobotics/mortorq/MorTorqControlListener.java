package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.TimeoutEmitter;
import com.bhrobotics.morlib.HelloListener;

class MorTorqControlListener extends ControlListener {
    private TimeoutEmitter endGameTimeout             = new TimeoutEmitter();
    private MorTorqTouchPanelFilter panelFilter       = new MorTorqTouchPanelFilter();
    private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    private MastListener mastListener                 = new MastListener();
    private MinibotListener minibotListener           = new MinibotListener();
    private ElbowListener elbowListener               = new ElbowListener();
    private ClawListener clawListener                 = new ClawListener();
    private WristListener wristListener               = new WristListener();
    
    public void startAutonomous() {
        mecanumDriveListener.stop();
        mastListener.stop();
        minibotListener.reset();
        elbowListener.reset();
        clawListener.reset();
        wristListener.reset();
    }
    
    public void startOperatorControl() {
        joystickFilter.bind("updateJoystick1", mecanumDriveListener);
        dsInputFilter.bind("all", panelFilter);
        
        endGameTimeout.bind("startEndGame", minibotListener);
        endGameTimeout.schedule("startEndGame", 110);
        
        panelFilter.bind("all", mecanumDriveListener);
        panelFilter.bind("all", mastListener);
        panelFilter.bind("all", minibotListener);
        panelFilter.bind("all", elbowListener);
        panelFilter.bind("all", clawListener);
        panelFilter.bind("all", wristListener);
    }
    
    public void stopOperatorControl() {
        joystickFilter.unbind("updateJoystick1", mecanumDriveListener);
        dsInputFilter.unbind("all", panelFilter);
        
        endGameTimeout.unbind("startEndGame", minibotListener);
        endGameTimeout.cancelAll();
        
        panelFilter.unbind("all", mecanumDriveListener);
        panelFilter.unbind("all", mastListener);
        panelFilter.unbind("all", minibotListener);
        panelFilter.unbind("all", elbowListener);
        panelFilter.unbind("all", clawListener);
        panelFilter.unbind("all", wristListener);
    }
}
