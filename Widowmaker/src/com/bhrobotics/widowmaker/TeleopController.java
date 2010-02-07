package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;
import com.bhrobotics.morlib.OperatorInterface;

// Controls the robot during teleop mode.
public class TeleopController extends Controller {

    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;

    //**************************************************************************
    // OI inputs
    //**************************************************************************

    private static final int DRIVE_STICK = 1;
    private static final int AIM_STICK = 2;

    private static final int STOP_BUTTON = 1;
    private static final int FIRE_BUTTON = 2;
    private static final int ROLLER_BUTTON = 3;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public TeleopController(DriveTrain _driveTrain, Carney _carney,
                            Roller _roller) {
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
    }

    public boolean isStopped(OperatorInterface oi) {
        return oi.getDigitalIn(STOP_BUTTON);
    }

    public void start() {
        driveTrain.start();
        carney.start();
        roller.start();
    }

    public void running(OperatorInterface oi) {

        // Drive train controls
        double x = oi.getJoystick(DRIVE_STICK).getX();
        double y = oi.getJoystick(DRIVE_STICK).getY();
        double rotation = oi.getJoystick(AIM_STICK).getX();
        driveTrain.mecanum(x, y, rotation);

        // Carney controls
        carney.checkLimits();
        if(oi.getDigitalIn(FIRE_BUTTON)) { carney.fire(); }

        // Roller controls
        if(oi.getDigitalIn(ROLLER_BUTTON)) {
            roller.roll();
        }else{
            roller.stop();
        }
    }

    public void stop() {
        driveTrain.stop();
        carney.stop();
        roller.stop();
    }
}