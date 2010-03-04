package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;

// Deflects the balls when driving and retract while going over a bump.
public class Deflector implements Model {
    private boolean deflector;

    // True means the deflector should be down, false means up.
    public void start() { stop(); }
    public void stop() { deflector = false; }

    public void set(boolean _deflector) { deflector = _deflector; }
    public boolean get() { return deflector; }
}