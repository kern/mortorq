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
    private static final int TOP_LIMIT = 13;
    private static final int BOTTOM_LIMIT = 14;

    private DigitalInput topLimit = new DigitalInput(LIMIT_SLOT, TOP_LIMIT);
    private DigitalInput bottomLimit = new DigitalInput(LIMIT_SLOT, BOTTOM_LIMIT);

    //**************************************************************************
    // Relays
    //**************************************************************************

    private static final int RELAY_SLOT = 4;
    private static final int RELAY = 1;
    
    private Relay relay = new Relay(RELAY_SLOT, RELAY);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public CarneyView(Carney _carney) {
        carney = _carney;
    }
    
    public void update() {
        carney.setTopLimit(!topLimit.get());
        carney.setBottomLimit(!bottomLimit.get());
    }

    public void render() {
        if(carney.getTopLimit()) {
            relay.set(Relay.Value.kReverse);
        }else if(carney.getBottomLimit()) {
            relay.set(Relay.Value.kForward);
        }else{
            relay.set(Relay.Value.kOff);
        }
    }
}