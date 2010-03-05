package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.OperatorInterface;

public class TouchInterface extends OperatorInterface {

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

    public double strafe() { return joystick1.getX(); }
    public double speed() { return joystick1.getY(); }
    public double rotation() { return joystick1.getZ(); }

    public boolean carneyManual() {
        return inputOn(MANIPULATORS, 7) || inputOn(MANIPULATORS_DEMO,4 );
    }

    // If true, the kicker should kick if the top limit is hit.
    public boolean kick() {
        return joyButton(11) || inputOn(GAME, 4) || inputOn(MANIPULATORS, 6) || inputOn(MANIPULATORS_DEMO, 7);
    }

    // If true, the kicker should force retract.
    public boolean retract() {
        return inputOn(GAME, 5) || inputOn(MANIPULATORS, 5) || inputOn(MANIPULATORS_DEMO, 6);
    }

    // If true, the kicker should force extend.
    public boolean extend() {
        return inputOn(MANIPULATORS, 4) || inputOn(MANIPULATORS_DEMO, 5);
    }

    // If true, the compressor is set to the value of compressor()
    public boolean compressorManual() {
        return inputOn(MANIPULATORS, 8) || inputOn(MANIPULATORS_DEMO, 8);
    }

    // When true, the compressor should be on if the compressor is in manual.
    public boolean compressor() {
        return inputOff(MANIPULATORS, 9) || inputOn(MANIPULATORS_DEMO, 9);
    }

    // When true, the deflectors should be down.
    public boolean deflector() {
        return inputOn(GAME, 8) || inputOn(MANIPULATORS, 10);
    }

    // Says how fast the rollers should go.
    public double roller() {
        if(joyButton(2) || inputOn(GAME, 7) || inputOn(MANIPULATORS, 13) || inputOn(MANIPULATORS, 14)) {
            int direction = 1;
            if(inputOn(MANIPULATORS, 12)) {
                direction = -1;
            }

            double speed = 0.75;
            if(inputOff(GAME, 6) || inputOn(MANIPULATORS, 14)) {
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
        if(inputOn(DRIVE_TRAIN, 8)) { return speed; }
        return -speed;
    }

    public double rightBack() {
        double speed = 0.5;
        if(inputOn(DRIVE_TRAIN, 15)) { speed = 1.0; }

        if(inputOn(DRIVE_TRAIN, 13)) { return 0.0; }
        if(inputOn(DRIVE_TRAIN, 14)) { return speed; }
        return -speed;
    }

    public double leftFront() {
        double speed = 0.5;
        if(inputOn(DRIVE_TRAIN, 6)) { speed = 1.0; }

        if(inputOn(DRIVE_TRAIN, 4)) { return 0.0; }
        if(inputOn(DRIVE_TRAIN, 5)) { return speed; }
        return -speed;
    }

    public double leftBack() {
        double speed = 0.5;
        if(inputOn(DRIVE_TRAIN, 12)) { speed = 1.0; }

        if(inputOn(DRIVE_TRAIN, 10)) { return 0.0; }
        if(inputOn(DRIVE_TRAIN, 11)) { return speed; }
        return -speed;
    }
}