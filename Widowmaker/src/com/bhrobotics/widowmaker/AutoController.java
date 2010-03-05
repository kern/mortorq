package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.*;
import edu.wpi.first.wpilibj.Timer;

// Controls the robot during autonomous mode.
public class AutoController extends Controller {

    private Compressor compressor;
    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;
    private Deflector deflector;

    private Timer timer = new Timer();

    public AutoController(Compressor _compressor, DriveTrain _driveTrain,
                          Carney _carney, Roller _roller,
                          Deflector _deflector) {
        compressor  = _compressor;
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
        deflector = _deflector;
    }

    public void start() {
        timer.reset();
        timer.start();
    }

    public void run() {
        /*roller.set(1.0);
        
        if(timer.get() < 3) {
            driveTrain.mecanum(0.0, 1.0, 0.0);
        }else{
            driveTrain.stop();
        }*/
    }

    public void stop() {
        timer.stop();
    }
}