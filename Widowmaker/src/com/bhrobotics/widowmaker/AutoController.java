package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;

// Controls the robot during autonomous mode.
public class AutoController extends Controller {

    private Compressor compressor;
    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;
    private Deflector deflector;

    public AutoController(Compressor _compressor, DriveTrain _driveTrain,
                          Carney _carney, Roller _roller,
                          Deflector _deflector) {
        compressor  = _compressor;
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
        deflector = _deflector;
    }
}