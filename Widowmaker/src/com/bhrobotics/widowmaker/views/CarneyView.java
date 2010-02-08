package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.Carney;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

// Controls Carney.
public class CarneyView implements View {

    private Carney carney;

    //**************************************************************************
    // Inputs
    //**************************************************************************

    private static final int LIMIT_SLOT = 4;
    private static final int TOP_LIMIT = 1;
    private static final int BOTTOM_LIMIT = 2;

    private DigitalInput topLimit = new DigitalInput(LIMIT_SLOT, TOP_LIMIT);
    private DigitalInput bottomLimit = new DigitalInput(LIMIT_SLOT, BOTTOM_LIMIT);

    //**************************************************************************
    // Outputs
    //**************************************************************************

    private static final int COMPRESSOR_SLOT = 6;
    private static final int COMPRESSOR = 1;

    //private Compressor compressor = new Compressor(COMPRESSOR_SLOT, COMPRESSOR);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public CarneyView(Carney _carney) {
        carney = _carney;
    }
    
    public void update() {
        carney.setTopLimit(topLimit.get());
        carney.setBottomLimit(bottomLimit.get());
    }

    public void render() {
        if(carney.getCompressor()) {
            //compressor.set(Relay.Value.kOff);
        }else{
            //compressor.set(Relay.Value.kOff);
        }
    }
}