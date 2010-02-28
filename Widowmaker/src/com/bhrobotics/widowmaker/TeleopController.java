package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;
import com.bhrobotics.morlib.OperatorInterface;

// Controls the robot during teleop mode.
public class TeleopController extends Controller {

    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;
    private Deflector deflector;

    //**************************************************************************
    // Button State
    //**************************************************************************

    private boolean previous_deflector = false;

    //**************************************************************************
    // OI inputs
    //**************************************************************************

    private static final int DRIVE_STICK = 1;
    private static final int STRAFE_STICK = 2;

    private static final int STOP_BUTTON = 1;
    private static final int ROLLER_BUTTON = 3;
    private static final int DEFLECTOR_BUTTON = 2;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public TeleopController(OperatorInterface _oi, DriveTrain _driveTrain,
                            Carney _carney, Roller _roller,
                            Deflector _deflector) {
        super(_oi);
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
        deflector = _deflector;
    }

    public boolean isStopped() {
        return oi.getStopped();
    }

    public void start() {
        driveTrain.start();
        carney.start();
        roller.start();
        deflector.start();
    }

    public void newData() {

        // Drive train controls
        double x = oi.getJoystick(STRAFE_STICK).getX();
        double y = oi.getJoystick(DRIVE_STICK).getY();
        double rotation = oi.getJoystick(DRIVE_STICK).getX();
        driveTrain.mecanum(x, y, rotation);

        // Carney controls
        if(oi.getJoystick(DRIVE_STICK).getTrigger() ||
           oi.getJoystick(STRAFE_STICK).getTrigger() ||
           oi.getCoil(5, 1)) {
            carney.fireSix();
        }else{
            carney.retract();
        }

        // Roller controls
        if(oi.getJoystick(DRIVE_STICK).getRawButton(ROLLER_BUTTON) ||
           oi.getJoystick(STRAFE_STICK).getRawButton(ROLLER_BUTTON)) {
            roller.rollIn();
        }else if(oi.getJoystick(DRIVE_STICK).getRawButton(10) ||
                 oi.getJoystick(STRAFE_STICK).getRawButton(10)) {
            roller.rollOut();
        }else{
            roller.stop();
        }

        // Deflector controls
        if(previous_deflector == false &&
           (oi.getJoystick(DRIVE_STICK).getRawButton(DEFLECTOR_BUTTON) ||
            oi.getJoystick(STRAFE_STICK).getRawButton(DEFLECTOR_BUTTON))) {
            if(deflector.getDeflector()) {
                deflector.up();
            }else{
                deflector.down();
            }
        }
        previous_deflector = oi.getJoystick(DRIVE_STICK).getRawButton(DEFLECTOR_BUTTON) ||
                             oi.getJoystick(STRAFE_STICK).getRawButton(DEFLECTOR_BUTTON);
    }

    public void stop() {
        driveTrain.stop();
        carney.stop();
        roller.stop();
        deflector.stop();
    }
}