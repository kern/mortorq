package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;

public class Roller implements Model {

    private static final double SPEED = 0.1;

    private double roller = 0;

    public void start() { stop(); }
    public void stop() { roller = 0.0; }

    public void roll() {
        roller = SPEED;
    }

    public double getRoller() { return roller; }
}