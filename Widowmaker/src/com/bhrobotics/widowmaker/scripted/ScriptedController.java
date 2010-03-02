package com.bhrobotics.widowmaker.scripted;

import com.bhrobotics.morlib.Controller;
import com.bhrobotics.widowmaker.models.Carney;
import com.bhrobotics.widowmaker.models.DriveTrain;
import com.bhrobotics.widowmaker.models.Roller;

/**
 * Controls the robot during autonomous mode, based on a "script" (composed
 * of simple objects, rather than text).
 */
public class ScriptedController extends Controller {

    private DriveTrain driveTrain;
    private Carney carney;
    private Roller roller;
    
    private Block runner;

    /**
     *
     * @param _driveTrain
     * @param _carney
     * @param _roller
     */
    public ScriptedController(DriveTrain _driveTrain,
            Carney _carney, Roller _roller) {
        driveTrain = _driveTrain;
        carney = _carney;
        roller = _roller;
    }

    /**
     * Called before the robot is started or stopped.
     */
    public void init() {
        // Set up script runner
        runner = new Block();
        runner.add(new SetRoller(roller, SetRoller.ON));
        runner.add(new SetDrive(driveTrain, 1.0, 0.0, 0.0));
        runner.add(new Sleep(2.1));
        runner.add(new SetDrive(driveTrain, 0.0, 0.0, 0.0));
        runner.add(new Sleep(0.15)); // Finish stopping
        runner.add(new SetRoller(roller, SetRoller.OFF));
        runner.add(new Sleep(0.05)); // Let roller slow down
        runner.add(new Kick(carney));
    }

    /**
     * Called continuously while the robot is running.
     */
    public void running() {
        runner.execute();
    }
}
