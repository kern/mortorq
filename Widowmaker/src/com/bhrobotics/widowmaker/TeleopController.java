package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;
import com.bhrobotics.morlib.OperatorInterface;

// Controls the robot during teleop mode.
public class TeleopController extends Controller {

    private TouchInterface oi;
    private Compressor compressor;
    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;
    private Deflector deflector;

    //**************************************************************************
    // Interface
    //**************************************************************************

    public TeleopController(OperatorInterface _oi, Compressor _compressor,
                            DriveTrain _driveTrain, Carney _carney,
                            Roller _roller, Deflector _deflector) {
        oi = (TouchInterface) _oi;
        compressor = _compressor;
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
        deflector = _deflector;
    }

    public void start() {
        compressor.start();
        driveTrain.start();
        carney.start();
        roller.start();
        deflector.start();
    }

    public void newData() {

        // Compressor
        compressor.setAuto(!oi.compressorManual());
        compressor.setManual(oi.compressor());

        // Drive train
        if(oi.driveManual()) {
            driveTrain.setRightFront(oi.rightFront());
            driveTrain.setRightBack(oi.rightBack());
            driveTrain.setLeftFront(oi.leftFront());
            driveTrain.setLeftBack(oi.leftBack());
        }else{
            double strafe = oi.strafe();
            double speed = oi.speed();
            double rotation = oi.rotation();
            driveTrain.mecanum(strafe, speed, rotation);
        }

        // Carney controls
        if(oi.kick()) {
            carney.fireSix();
        }else if(oi.extend()) {
            carney.forceFire();
        }else if(oi.retract()) {
            carney.forceRetract();
        }else{
            carney.retract();
        }

        roller.set(oi.roller());
        deflector.set(oi.deflector());
    }

    public void stop() {
        compressor.stop();
        driveTrain.stop();
        carney.stop();
        roller.stop();
        deflector.stop();
    }
}