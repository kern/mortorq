package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.TimeoutEmitter;
import com.bhrobotics.morlib.HelloListener;

class MorTorqControlListener extends ControlListener {
    // private TimeoutEmitter endGameTimer               = new TimeoutEmitter();
    // private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    // private MinibotListener minibotListener           = new MinibotListener();
    // private ElbowListener elbowListener               = new ElbowListener();
    // private ClawListener clawListener                 = new ClawListener();
    // private WristListener wristListener               = new WristListener();
    private HelloListener helloListener = new HelloListener();
    
    public void startAutonomous() {
        // elbowListener.lower();
    }
    
    public void startOperatorControl() {
        dsInputFilter.bind("updateDigital1", helloListener);
        // joystickEmitter.bind("updateJoystick1", mecanumDriveListener);
        // endGameTimeout.bind("startEndGame", minibotListener);
        // endGameTimer.schedule("startEndGame", 110);
    }
    
    public void stopOperatorControl() {
        dsInputFilter.unbind("updateDigital1", helloListener);
        // joystickEmitter.unbind("updateJoystick1", mecanumDriveListener);
        // endGameTimer.unbind("startEndGame", minibotListener);
        // endGameTImer.cancelAll();
        // minibotListener.reset();
    }
}