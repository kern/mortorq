package com.bhrobotics.widowmaker;

import com.bhrobotics.widowmaker.model.OperatorInterface;
import com.bhrobotics.widowmaker.model.DriveTrain;
import com.bhrobotics.widowmaker.model.Carney;

import com.bhrobotics.widowmaker.view.FourWheelView;
import com.bhrobotics.widowmaker.view.CarneyView;

/**
 * Handles the station used by robot operators, containing the joysticks,
 * buttons, switches, etc., that are used to control the robot's actions in
 * teleoperated mode.
 **/
public class TeleopController extends RobotController {

    private OperatorInterface oi;
    private DriveTrain driveTrain;
    private Carney carney;
    
    public TeleopController() {
        oi = new OperatorInterface();
        driveTrain = new DriveTrain();
        carney = new Carney();
    }

    protected boolean getEmergencyStop() {
        return oi.getEmergencyStop();
    }

    protected void emergencyStop() {
        driveTrain.emergencyStop();
        carney.emergencyStop();
    }

    protected void continuous() {
        driveTrain.mecanum(oi.getX(), oi.getY(), oi.getRotation());
    }

    protected void render() {
        FourWheelView.render(driveTrain);
        CarneyView.render(carney);
    }
}