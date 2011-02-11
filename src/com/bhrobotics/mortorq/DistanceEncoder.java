package com.bhrobotics.mortorq;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Encoder;

public class DistanceEncoder extends Encoder implements PIDSource {
    public DistanceEncoder(int aSlot, int aChannel, int bSlot, int bChannel, boolean reverseDirection) {
        super(aSlot, aChannel, bSlot, bChannel, reverseDirection, Encoder.EncodingType.k1X);
    }
    
    public double pidGet() {
        return getRate();
    }
}