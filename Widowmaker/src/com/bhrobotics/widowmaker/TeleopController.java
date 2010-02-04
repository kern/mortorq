package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;

// Controls the robot during teleop mode.
public class TeleopController extends Controller {

    private OperatorInterface oi;
    private Crio crio;
    private DriveTrain driveTrain;
    private Carney carney;

    public TeleopController(OperatorInterface _oi, Crio _crio,
                            DriveTrain _driveTrain, Carney _carney) {
        oi = _oi;
        crio = _crio;
        driveTrain = _driveTrain;
        carney = _carney;
    }

    protected void start() {
        driveTrain.start();
        carney.start();
    }

    protected void stop() {
        driveTrain.stop();
        carney.stop();
    }

    protected void running() {
        driveTrain.mecanum(oi.getX(), oi.getY(), oi.getRotation());

        // TODO: Carney code goes here
    }

    protected void render() {
        // TODO: Render the views
    }
}