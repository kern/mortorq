package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.EventedRobot;
import com.bhrobotics.morlib.Listener;

class MorTorqRobot extends EventedRobot {
    private Listener controlListener = new ControlListener();
}