package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Robot;
import com.bhrobotics.widowmaker.models.*;

public class Widowmaker extends Robot {

    Carney carney = new Carney();
    Crio crio = new Crio();
    DriveTrain driveTrain = new DriveTrain();
    OperatorInterface oi = new OperatorInterface();

    public Widowmaker() {
        autoController = new AutoController();
        teleopController = new TeleopController();
    }

}