package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.DriveTrain;
import com.bhrobotics.widowmaker.models.Carney;

// Controls the robot during teleop mode.
public class TeleopController extends Controller {

    private DriveTrain driveTrain;
    private Carney carney;

    public TeleopController(DriveTrain _driveTrain, Carney _carney) {
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
        
        if(oi.getFire()) { carney.fire(); }
    }
}