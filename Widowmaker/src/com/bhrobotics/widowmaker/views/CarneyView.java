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
    private static final int FIRST_FIRE_SOLENOID = 1;
    private static final int SECOND_FIRE_SOLENOID = 2;
    private static final int RETRACT_SOLENOID = 3;

    private Solenoid firstFireSolenoid = new Solenoid(SOLENOID_SLOT, FIRST_FIRE_SOLENOID);
    private Solenoid secondFireSolenoid = new Solenoid(SOLENOID_SLOT, SECOND_FIRE_SOLENOID);
    private Solenoid retractSolenoid = new Solenoid(SOLENOID_SLOT, RETRACT_SOLENOID);

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
        firstFireSolenoid.set(carney.getCarney());
        secondFireSolenoid.set(carney.getCarney());
        retractSolenoid.set(!carney.getCarney());

        if(carney.getTopLimit()) {
            relay.set(Relay.Value.kReverse);
        }else if(carney.getBottomLimit()) {
            relay.set(Relay.Value.kForward);
        }else{
            relay.set(Relay.Value.kOff);
        }
    }
}