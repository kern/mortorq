package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.TimeoutEmitter;
import com.bhrobotics.morlib.HelloListener;

class MorTorqControlListener extends ControlListener {
    private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    private TimeoutEmitter endGameTimer = new TimeoutEmitter();
    private MinibotListener minibotListener = new MinibotListener();
    private ElbowListener elbowListener = new ElbowListener();
    private ClawListener clawListener = new ClawListener();
    private WristListener wristListener = new WristListener();
    
    public void startAutonomous() {
        elbowListener.lower();
    }
    
    public void startOperatorControl() {
        joystickEmitter.addListener("updateJoystick1", mecanumDriveListener);
        endGameTimer.addListener("timeout", minibotListener);
        endGameTimer.start(110);
    }
    
    public void stopOperatorControl() {
        joystickEmitter.removeListener("updateJoystick1", mecanumDriveListener);
        endGameTimer.removeListener("timeout", minibotListener);
        minibotListener.reset();
    }
}