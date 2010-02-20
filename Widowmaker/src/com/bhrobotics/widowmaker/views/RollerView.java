package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.Roller;
import edu.wpi.first.wpilibj.Victor;

public class RollerView implements View {

    private Roller roller;

    //**************************************************************************
    // Outputs
    //**************************************************************************

    private static final int SLOT = 6;
    private static final int POLARITY = 1;
    private static final int CIM = 6;

    private Victor cim = new Victor(SLOT, CIM);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public RollerView(Roller _roller) {
        roller = _roller;
    }

    public void update() {}

    public void render() {
        cim.set(roller.getRoller() * POLARITY);
    }
}