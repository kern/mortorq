package com.bhrobotics.widowmaker;

import com.bhrobotics.widowmaker.view.DashboardView;

// Controls the robot during autonomous mode.
public class AutoController extends RobotController {

    // In autonomous mode, the emergency stop must be disabled since there's no
    // way to get any data from the operator interface at this time.
    boolean isStopped() { return false; }
    
    void stopped() {}
    
    void running() {}

    void render() {
        DashboardView.render();
    }
}