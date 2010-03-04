package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.Deflector;
import edu.wpi.first.wpilibj.Solenoid;

// Controls the deflector.
public class DeflectorView implements View {

    private Deflector deflector;

    //**************************************************************************
    // Solenoids
    //**************************************************************************

    private static final int SOLENOID_SLOT = 8;
    private static final int DOWN = 4;
    private static final int UP = 5;

    private Solenoid downSolenoid = new Solenoid(SOLENOID_SLOT, DOWN);
    private Solenoid upSolenoid = new Solenoid(SOLENOID_SLOT, UP);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public DeflectorView(Deflector _deflector) {
        deflector = _deflector;
    }

    public void update() {}

    public void render() {
        downSolenoid.set(!deflector.get());
        upSolenoid.set(deflector.get());
    }
}