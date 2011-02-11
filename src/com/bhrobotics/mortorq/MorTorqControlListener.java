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
    private MinibotListener minibotListener           = new MinibotListener();
    private ElbowListener elbowListener               = new ElbowListener();
    private ClawListener clawListener                 = new ClawListener();
    private WristListener wristListener               = new WristListener();
    
    public void startAutonomous() {
        elbowListener.lower();
    }
    
    public void startOperatorControl() {
        joystickFilter.bind("updateJoystick1", mecanumDriveListener);
        dsInputFilter.bind("all", panelFilter);
        endGameTimeout.bind("startEndGame", minibotListener);
        endGameTimeout.schedule("startEndGame", 110);
    }
    
    public void stopOperatorControl() {
        joystickFilter.unbind("updateJoystick1", mecanumDriveListener);
        dsInputFilter.unbind("all", panelFilter);
        endGameTimeout.unbind("startEndGame", minibotListener);
        endGameTimeout.cancelAll();
        minibotListener.reset();
    }
}
