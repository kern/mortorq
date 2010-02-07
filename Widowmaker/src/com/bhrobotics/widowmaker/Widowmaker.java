package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Robot;
import com.bhrobotics.widowmaker.models.*;
import com.bhrobotics.widowmaker.views.*;

public class Widowmaker extends Robot {

    private Crio crio = new Crio();
    private DriveTrain driveTrain = new DriveTrain();
    private Carney carney = new Carney();
    private Roller roller = new Roller();

    public Widowmaker() {
        autoController = new AutoController(driveTrain, carney, roller);
        teleopController = new TeleopController(driveTrain, carney, roller);

        addView(new DashboardView(crio));
        addView(new FourWheelView(driveTrain));
        addView(new CarneyView(carney));
        addView(new RollerView(roller));
    }
}