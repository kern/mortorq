package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.Roller;
import edu.wpi.first.wpilibj.Jaguar;

public class RollerView implements View {

    private Roller roller;

    //**************************************************************************
    // Outputs
    //**************************************************************************

    private static final int RIGHT = 5;
    private static final int LEFT = 6;

    private Jaguar right = new Jaguar(RIGHT);
    private Jaguar left = new Jaguar(LEFT);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public RollerView(Roller _roller) {
        roller = _roller;
    }

    // Rollers are wired with reverse polarity.
    public void render() {
        right.set(roller.getRoller());
        left.set(roller.getRoller());
    }
}