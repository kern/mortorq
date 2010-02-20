package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.Carney;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;

// Controls Carney.
public class CarneyView implements View {

    private Carney carney;

    //**************************************************************************
    // Solenoids
    //**************************************************************************

    private static final int SOLENOID_SLOT = 8;
    private static final int FIRE_TWO = 6;
    private static final int FIRE_FOUR = 7;
    private static final int RETRACT = 8;

    private Solenoid fireTwoSolenoid = new Solenoid(SOLENOID_SLOT, FIRE_TWO);
    private Solenoid fireFourSolenoid = new Solenoid(SOLENOID_SLOT, FIRE_FOUR);
    private Solenoid retractSolenoid = new Solenoid(SOLENOID_SLOT, RETRACT);

    //**************************************************************************
    // Inputs
    //**************************************************************************

    private static final int LIMIT_SLOT = 4;
    private static final int TOP_LIMIT = 14;
    private static final int BOTTOM_LIMIT = 13;

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
        fireTwoSolenoid.set(carney.getFireTwo());
        fireFourSolenoid.set(carney.getFireFour());
        retractSolenoid.set(carney.getRetract());

        if(carney.getTopLimit()) {
            relay.set(Relay.Value.kReverse);
        }else if(carney.getBottomLimit()) {
            relay.set(Relay.Value.kForward);
        }else{
            relay.set(Relay.Value.kOff);
        }
    }
}