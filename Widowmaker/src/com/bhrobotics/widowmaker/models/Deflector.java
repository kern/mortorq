package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;

// Deflects the balls when driving and retract while going over a bump.
public class Deflector implements Model {

    private boolean deflector;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public void start() { down(); }
    public void stop() {}

    public void down() { deflector = true; }
    public void up() { deflector = false; }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public boolean getDeflector() { return deflector; }
}