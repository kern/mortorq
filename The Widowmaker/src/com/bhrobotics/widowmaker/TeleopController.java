package com.bhrobotics.widowmaker;

import com.bhrobotics.widowmaker.model.Crio;
import com.bhrobotics.widowmaker.model.OperatorInterface;
import com.bhrobotics.widowmaker.model.DriveTrain;
import com.bhrobotics.widowmaker.model.carney.Carney;

import com.bhrobotics.widowmaker.view.DashboardView;
import com.bhrobotics.widowmaker.view.FourWheelView;
import com.bhrobotics.widowmaker.view.CarneyView;

/**
 * Handles the station used by robot operators, containing the joysticks,
 * buttons, switches, etc., that are used to control the robot's actions in
 * teleoperated mode.
 **/
public class TeleopController extends RobotController {

    private Crio crio;
    private OperatorInterface oi;
    private DriveTrain driveTrain;
    private Carney carney;
    
    public TeleopController() {
        crio = Crio.getInstance();
        oi = OperatorInterface.getInstance();
        driveTrain = DriveTrain.getInstance();
        carney = Carney.getInstance();
    }

    protected boolean getEmergencyStop() {
        return oi.getEmergencyStop();
    }

    protected void emergencyStop() {
        driveTrain.emergencyStop();
        carney.emergencyStop();
    }

    protected void exitEmergencyStop() {
        carney.exitEmergencyStop();
    }

    protected void continuous() {
        driveTrain.mecanum(oi.getX(), oi.getY(), oi.getRotation());

        carney.checkLimits();
        if(oi.getFire()) { carney.fire(); }
    }

    protected void render() {
        DashboardView.render(crio);
        FourWheelView.render(driveTrain);
        CarneyView.render(carney);
    }
}