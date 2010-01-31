package com.bhrobotics.widowmaker;

public class AutoController extends RobotController {

    // In autonomous mode, the emergency stop must be disabled since there's no
    // way to get any data from the operator interface at this time.
    protected boolean getEmergencyStop() {
        return false;
    }
}