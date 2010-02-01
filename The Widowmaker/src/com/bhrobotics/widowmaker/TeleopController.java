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

    boolean isStopped() { return OperatorInterface.getEmergencyStop(); }

    void stoppedInit() {
        Carney.stoppedInit();
    }

    void stopped() {
        DriveTrain.stopped();
    }

    void runningInit() {
        Carney.runningInit();
    }

    void running() {
        double x = OperatorInterface.getX();
        double y = OperatorInterface.getY();
        double rotation = OperatorInterface.getRotation();
        DriveTrain.mecanum(x, y, rotation);

        Carney.checkLimits();
        if(OperatorInterface.getFire()) { Carney.fire(); }
    }

    void render() {
        DashboardView.render();
        FourWheelView.render();
        CarneyView.render();
    }
}