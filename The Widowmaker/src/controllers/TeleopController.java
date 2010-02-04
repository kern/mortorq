package com.bhrobotics.widowmaker;

import com.bhrobotics.widowmaker.model.Crio;
import com.bhrobotics.widowmaker.model.OperatorInterface;
import com.bhrobotics.widowmaker.model.DriveTrain;
import com.bhrobotics.widowmaker.model.carney.Carney;

import com.bhrobotics.widowmaker.view.DashboardView;
import com.bhrobotics.widowmaker.view.FourWheelView;
import com.bhrobotics.widowmaker.view.CarneyView;

// Handles the station used by robot operators, containing the joysticks,
// buttons, switches, etc., that are used to control the robot's actions in
// teleoperated mode.
class TeleopController extends RobotController {

    public OperatorInterface oi = new OperatorInterface();
    public DriveTrain driveTrain = new DriveTrain();
    public Carney carney = new Carney();

    boolean isActive() { return !oi.getStop(); }
    
    void start() {
        carney.start();
    }

    void stop() {
        carney.stop();
        driveTrain.stop();
    }

    void running() {
        driveTrain.mecanum(oi.getX(), oi.getY(), oi.getRotation());

        carney.checkLimits();
        if(oi.getFire()) { carney.fire(); }
    }

    void render() {
        DashboardView.render();
        FourWheelView.render();
        CarneyView.render();
    }
}