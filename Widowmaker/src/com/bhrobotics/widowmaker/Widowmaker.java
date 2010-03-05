package com.bhrobotics.widowmaker;

import com.bhrobotics.morlib.Robot;
import com.bhrobotics.widowmaker.models.*;
import com.bhrobotics.widowmaker.views.*;

public class Widowmaker extends Robot {

    private Crio crio = new Crio();
    private Compressor compressor = new Compressor();
    private DriveTrain driveTrain = new DriveTrain();
    private Carney carney = new Carney();
    private Roller roller = new Roller();
    private Deflector deflector = new Deflector();

    public Widowmaker() {

        oi = new TouchInterface();
        autoController = new AutoController(compressor, driveTrain, carney, roller, deflector);
        teleopController = new TeleopController(oi, compressor, driveTrain, carney, roller, deflector);

        addView(new DashboardView(crio));
        addView(new CompressorView(compressor));
        addView(new FourWheelView(driveTrain));
        addView(new CarneyView(carney));
        addView(new RollerView(roller));
        addView(new DeflectorView(deflector));
    }
}