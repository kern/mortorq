package com.bhrobotics.widowmaker.view;

import edu.wpi.first.wpilibj.Jaguar;
import com.bhrobotics.widowmaker.model.DriveTrain;

// Uses four wheels to control a robot.
public class FourWheelView {
    
    private static final int RIGHT_FRONT = 1;
    private static final int RIGHT_BACK = 2;
    private static final int LEFT_FRONT = 3;
    private static final int LEFT_BACK = 4;
    
    private static Jaguar rightFront = new Jaguar(RIGHT_FRONT);
    private static Jaguar rightBack = new Jaguar(RIGHT_BACK);
    private static Jaguar leftFront = new Jaguar(LEFT_FRONT);
    private static Jaguar leftBack = new Jaguar(LEFT_BACK);
    
    public static void render(DriveTrain driveTrain) {
        rightFront.set(driveTrain.getRightFront());
        rightBack.set(driveTrain.getRightBack());
        leftFront.set(driveTrain.getLeftFront());
        leftBack.set(driveTrain.getLeftBack());
    }
}