package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.EventedRobot;
import com.bhrobotics.morlib.ControlListener;

class MorTorqRobot extends EventedRobot {
    private ControlListener controlListener = new MorTorqControlListener();
    
    public MorTorqRobot() {
        addControlListener(controlListener);
    }
}