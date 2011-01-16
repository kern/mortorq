package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Encoder;

public class RateEncoder extends Encoder implements PIDSource {
    public RateEncoder(int aSlot, int aChannel, int bSlot, int bChannel, boolean reverseDirection) {
        super(aSlot, aChannel, bSlot, bChannel, reverseDirection);
    }
    
    public double pidGet() {
        return getRate();
    }
}