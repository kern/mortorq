package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;

// Controls the robot during autonomous mode.
public class AutoController extends Controller {

    private DriveTrain driveTrain;
    private Carney carney;

    public AutoController(DriveTrain _driveTrain, Carney _carney) {
        driveTrain = _driveTrain;
        carney = _carney;
    }

}