package com.bhrobotics.widowmaker;

import com.bhrobotics.widowmaker.model.Crio;
import com.bhrobotics.widowmaker.view.DashboardView;

public class AutoController extends RobotController {

    private Crio crio;

    public AutoController() {
        crio = Crio.getInstance();
    }

    // In autonomous mode, the emergency stop must be disabled since there's no
    // way to get any data from the operator interface at this time.
    protected boolean getEmergencyStop() {
        return false;
    }

    protected void render() {
        DashboardView.render(crio);
    }
}