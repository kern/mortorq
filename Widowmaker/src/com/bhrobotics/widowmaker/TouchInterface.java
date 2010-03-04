package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.OperatorInterface;

public class TouchInterface extends OperatorInterface {

    private static final int DRIVE_STICK = 0;

    //**************************************************************************
    // Screens
    //**************************************************************************

    private static final int STOPPED = 1;
    private static final int GAME = 2;
    private static final int DRIVE_TRAIN = 3;
    private static final int MANIPULATORS = 4;
    private static final int DRIVE_TRAIN_DEMO = 5;
    private static final int MANIPULATORS_DEMO = 6;

    //**************************************************************************
    // Joystick
    //**************************************************************************

    public double strafe() { return joysticks[DRIVE_STICK].getX(); }
    public double speed() { return joysticks[DRIVE_STICK].getY(); }
    public double rotation() { return joysticks[DRIVE_STICK].getZ(); }

    // If true, the kicker should kick if the top limit is hit.
    public boolean kick() {
        boolean joy = joyButton(11);
        boolean game = inputOn(GAME, 4);
        return joy || game;
    }

    // If true, the kicker should force retract.
    public boolean retract() {
        boolean game = inputOn(GAME, 5);
        return game;
    }

    // If true, the kicker should force extend.
    public boolean extend() {
        boolean manipulators = inputOn(MANIPULATORS, 4);
        boolean manipulators_demo = inputOn(MANIPULATORS_DEMO, 5);
        return manipulators || manipulators_demo;
    }

    // If true, the compressor is set to the value of compressor()
    public boolean compressorManual() {
        boolean manipulators = inputOn(MANIPULATORS, 8);
        boolean manipulators_demo = inputOn(MANIPULATORS_DEMO, 8);
        return manipulators || manipulators_demo;
    }

    // When true, the compressor should be on if the compressor is in manual.
    public boolean compressor() {
        boolean manipulators = inputOn(MANIPULATORS, 9);
        boolean manipulators_demo = inputOn(MANIPULATORS_DEMO, 9);
        return manipulators || manipulators_demo;
    }

    // When true, the deflectors should be down.
    public boolean deflector() {
        boolean game = inputOff(GAME, 8);
        boolean manipulators = inputOff(MANIPULATORS, 10);
        return game || manipulators;
    }

    // Says how fast the rollers should go.
    public double roller() {
        if(joyButton(2) || inputOn(GAME, 7) || (inputOff(MANIPULATORS, 13) && inputOff(MANIPULATORS, 14))) {
            int direction = 1;
            if(inputOn(MANIPULATORS, 12)) {
                direction = -1;
            }

            double speed = 0.5;
            if(inputOn(GAME, 6) || inputOn(MANIPULATORS, 14)) {
                speed = 1.0;
            }

            return speed*direction;
        }
        return 0.0;
    }

    public boolean driveManual() {
        return screen == DRIVE_TRAIN_DEMO || screen == DRIVE_TRAIN;
    }

    public double rightFront() {
        double speed = 0.5;
        if(inputOn(DRIVE_TRAIN, 9)) { speed = 1.0; }

        if(inputOn(DRIVE_TRAIN, 7)) { return 0.0; }
        if(inputOn(DRIVE_TRAIN, 8)) { return -speed; }
        return speed;
    }

    public double rightBack() {
        double speed = 0.5;
        if(inputOn(DRIVE_TRAIN, 15)) { speed = 1.0; }

        if(inputOn(DRIVE_TRAIN, 13)) { return 0.0; }
        if(inputOn(DRIVE_TRAIN, 14)) { return -speed; }
        return speed;
    }

    public double leftFront() {
        double speed = 0.5;
        if(inputOn(DRIVE_TRAIN, 6)) { speed = 1.0; }

        if(inputOn(DRIVE_TRAIN, 4)) { return 0.0; }
        if(inputOn(DRIVE_TRAIN, 5)) { return -speed; }
        return speed;
    }

    public double leftBack() {
        double speed = 0.5;
        if(inputOn(DRIVE_TRAIN, 12)) { speed = 1.0; }

        if(inputOn(DRIVE_TRAIN, 10)) { return 0.0; }
        if(inputOn(DRIVE_TRAIN, 11)) { return -speed; }
        return speed;
    }
}