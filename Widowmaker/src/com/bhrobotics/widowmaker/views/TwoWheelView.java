package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.DriveTrain;
import edu.wpi.first.wpilibj.Jaguar;

// Uses two wheels to control a robot.
public class TwoWheelView implements View {

    private DriveTrain driveTrain;

    //**************************************************************************
    // Motors
    //**************************************************************************

    private static final int SLOT = 6;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;

    private Jaguar right = new Jaguar(SLOT, RIGHT);
    private Jaguar left = new Jaguar(SLOT, LEFT);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public TwoWheelView(DriveTrain _driveTrain) {
        driveTrain = _driveTrain;
    }

    public void update() {}

    public void render() {
        right.set(driveTrain.getRight());
        left.set(driveTrain.getLeft());
    }
}