package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;

class ControlListener implements Listener {
    public void handle(Event event) {
        System.out.println(event.getName());
    }
}