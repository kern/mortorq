package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;
import com.bhrobotics.morlib.OperatorInterface;

// Controls the robot during teleop mode.
public class TeleopController extends Controller {

    private TouchInterface oi;
    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;
    private Deflector deflector;

    //**************************************************************************
    // Button State
    //**************************************************************************

    private boolean previous_deflector = false;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public TeleopController(OperatorInterface _oi, DriveTrain _driveTrain,
                            Carney _carney, Roller _roller,
                            Deflector _deflector) {
        oi = (TouchInterface) _oi;
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
        deflector = _deflector;
    }

    public void start() {
        driveTrain.start();
        carney.start();
        roller.start();
        deflector.start();
    }

    public void newData() {

        // Drive train controls
        double x = oi.getX();
        double y = oi.getY();
        double rotation = oi.getZ();
        driveTrain.mecanum(x, y, rotation);

        // Carney controls
        if(oi.getKick()) {
            carney.fireSix();
        }else{
            carney.retract();
        }

        // Roller controls
        if(oi.getRollerDirection() == 1) {
            roller.rollIn();
        }else if(oi.getRollerDirection() == -1) {
            roller.rollOut();
        }else{
            roller.stop();
        }

        // Deflector controls
        if(previous_deflector == false && oi.getLeftDeflector()) {
            if(deflector.getDeflector()) {
                deflector.up();
            }else{
                deflector.down();
            }
        }
        previous_deflector = oi.getLeftDeflector();
    }

    public void stop() {
        driveTrain.stop();
        carney.stop();
        roller.stop();
        deflector.stop();
    }
}