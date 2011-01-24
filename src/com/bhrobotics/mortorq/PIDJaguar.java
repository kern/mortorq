package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.Jaguar;

public class PIDJaguar extends Jaguar {
    public PIDJaguar(final int slot, final int channel) {
        super(slot, channel);
    }
    
    public void pidWrite(double output) {
        set(get() + output);
    }
}