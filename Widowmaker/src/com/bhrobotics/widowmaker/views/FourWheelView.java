package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.DriveTrain;
import edu.wpi.first.wpilibj.Jaguar;

// Uses four wheels to control a robot.
public class FourWheelView implements View {

    private DriveTrain driveTrain;

    //**************************************************************************
    // Motors
    //**************************************************************************

    private static final int RIGHT_FRONT = 1;
    private static final int RIGHT_BACK = 2;
    private static final int LEFT_FRONT = 3;
    private static final int LEFT_BACK = 4;

    private Jaguar rightFront = new Jaguar(RIGHT_FRONT);
    private Jaguar rightBack = new Jaguar(RIGHT_BACK);
    private Jaguar leftFront = new Jaguar(LEFT_FRONT);
    private Jaguar leftBack = new Jaguar(LEFT_BACK);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public FourWheelView(DriveTrain _driveTrain) {
        driveTrain = _driveTrain;
    }

    public void render() {
        rightFront.set(driveTrain.getRightFront());
        rightBack.set(driveTrain.getRightBack());
        leftFront.set(driveTrain.getLeftFront());
        leftBack.set(driveTrain.getLeftBack());
    }
}