package com.bhrobotics.widowmaker.view;

import edu.wpi.first.wpilibj.Jaguar;
import com.bhrobotics.widowmaker.model.DriveTrain;

// Uses two wheels to control a robot.
public class TwoWheelView {

    private static final int RIGHT = 1;
    private static final int LEFT = 2;

    private Jaguar right;
    private Jaguar left;

    public TwoWheelView() {
        right = new Jaguar(RIGHT);
        left = new Jaguar(LEFT);
    }

    public void parse(DriveTrain driveTrain) {
        right.set(driveTrain.getRight());
        left.set(driveTrain.getLeft());
    }
}