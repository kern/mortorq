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
        compressor.setAuto(oi.getCompressorAuto());
        compressor.setManual(oi.getCompressor());

        // Drive train
        if(oi.getDriveAuto()) {
            double strafe = oi.getStrafe();
            double speed = oi.getSpeed();
            double rotation = oi.getRotation();
            driveTrain.mecanum(strafe, speed, rotation);
        }else{
            driveTrain.setRightFront(oi.getFrontRight());
            driveTrain.setRightBack(oi.getBackRight());
            driveTrain.setLeftFront(oi.getFrontLeft());
            driveTrain.setLeftBack(oi.getBackLeft());
        }

        // Carney controls
        if(oi.getKick()) {
            carney.fireSix();
        }else{
            carney.retract();
        }

        roller.set(oi.getRoller());
        deflector.set(oi.getDeflector());
    }

    public void stop() {
        compressor.stop();
        driveTrain.stop();
        carney.stop();
        roller.stop();
        deflector.stop();
    }
}