package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.TimeoutEmitter;
import com.bhrobotics.morlib.HelloListener;

class MorTorqControlListener extends ControlListener {
    private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    
    public void startOperatorControl() {
        joystickEmitter.addListener("updateJoystick1", mecanumDriveListener);
    }
    
    public void stopOperatorControl() {
        joystickEmitter.removeListener("updateJoystick1", mecanumDriveListener);
    }
}