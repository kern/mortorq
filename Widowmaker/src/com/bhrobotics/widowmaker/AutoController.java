package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;
import com.bhrobotics.morlib.OperatorInterface;

// Controls the robot during autonomous mode.
public class AutoController extends Controller {

    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;

    public AutoController(OperatorInterface _oi, DriveTrain _driveTrain,
                          Carney _carney, Roller _roller) {
        super(_oi);
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
    }

}