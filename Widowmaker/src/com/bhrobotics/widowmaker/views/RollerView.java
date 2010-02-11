package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.Roller;
import edu.wpi.first.wpilibj.Jaguar;

public class RollerView implements View {

    private Roller roller;

    //**************************************************************************
    // Outputs
    //**************************************************************************

    private static final int RIGHT_SLOT = 4;
    private static final int RIGHT_POLARITY = -1;
    private static final int RIGHT = 1;

    private static final int LEFT_SLOT = 6;
    private static final int LEFT_POLARITY = -1;
    private static final int LEFT = 6;

    private Jaguar right = new Jaguar(RIGHT_SLOT, RIGHT);
    private Jaguar left = new Jaguar(LEFT_SLOT, LEFT);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public RollerView(Roller _roller) {
        roller = _roller;
    }

    public void update() {}

    // Rollers are wired with reverse polarity.
    public void render() {
        right.set(roller.getRoller() * RIGHT_POLARITY);
        left.set(roller.getRoller() * LEFT_POLARITY);
    }
}