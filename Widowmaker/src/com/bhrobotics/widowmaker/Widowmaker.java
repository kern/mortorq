package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Robot;
import com.bhrobotics.widowmaker.models.*;

public class Widowmaker extends Robot {

    private OperatorInterface oi = new OperatorInterface();
    private Crio crio = new Crio();
    private DriveTrain driveTrain = new DriveTrain();
    private Carney carney = new Carney();

    public Widowmaker() {
        autoController = new AutoController(driveTrain, carney);
        teleopController = new TeleopController(oi, crio, driveTrain, carney);
    }

}