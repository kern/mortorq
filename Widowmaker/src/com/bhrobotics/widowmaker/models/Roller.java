package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;

public class Roller implements Model {
    private double roller = 0.0;

    public void start() { stop(); }
    public void stop() { roller = 0.0; }

    public void set(double speed) { roller = speed; }
    public double get() { return roller; }
}