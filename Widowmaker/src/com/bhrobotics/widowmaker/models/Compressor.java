package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;

public class Compressor implements Model {
    private boolean manual = false;
    private boolean sensor = false;
    private boolean auto = true;

    public void start() {}
    public void stop() {}

    public void setManual(boolean _manual) { manual = _manual; }
    public void setSensor(boolean _sensor) { sensor = _sensor; }
    public void setAuto(boolean _auto) { auto = _auto; }
    
    public boolean get() {
        if(auto) { return !sensor; }
        return manual;
    }
}