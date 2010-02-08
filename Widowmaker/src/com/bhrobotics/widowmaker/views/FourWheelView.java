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

    private static final int SLOT = 6;
    private static final int RIGHT_FRONT = 4;
    private static final int RIGHT_BACK = 2;
    private static final int LEFT_FRONT = 5;
    private static final int LEFT_BACK = 3;

    private Jaguar rightFront = new Jaguar(SLOT, RIGHT_FRONT);
    private Jaguar rightBack = new Jaguar(SLOT, RIGHT_BACK);
    private Jaguar leftFront = new Jaguar(SLOT, LEFT_FRONT);
    private Jaguar leftBack = new Jaguar(SLOT, LEFT_BACK);

    //**************************************************************************
    // Polarity
    //**************************************************************************

    private static final int RIGHT_FRONT_POLARITY = 1;
    private static final int RIGHT_BACK_POLARITY = 1;
    private static final int LEFT_FRONT_POLARITY = -1;
    private static final int LEFT_BACK_POLARITY = -1;

    //**************************************************************************
    // Interface
    //**************************************************************************
    
    public void update() {}

    public FourWheelView(DriveTrain _driveTrain) {
        driveTrain = _driveTrain;
    }

    public void render() {
        rightFront.set(driveTrain.getRightFront() * RIGHT_FRONT_POLARITY);
        rightBack.set(driveTrain.getRightBack() * RIGHT_BACK_POLARITY);
        leftFront.set(driveTrain.getLeftFront() * LEFT_FRONT_POLARITY);
        leftBack.set(driveTrain.getLeftBack() * LEFT_BACK_POLARITY);
    }
}