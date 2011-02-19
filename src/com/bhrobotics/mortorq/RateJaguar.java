package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.Jaguar;

public class RateJaguar extends Jaguar {
    public RateJaguar(final int slot, final int channel) {
        super(slot, channel);
    }
    
    public void pidWrite(double output) {
        set(get() + output);
    }
}