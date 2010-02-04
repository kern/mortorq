package com.bhrobotics.widowmaker.views;

import com.bhrobotics.widowmaker.models.Carney;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Servo;

// Controls Carney.
public class CarneyView {

    private Carney carney;

    //**************************************************************************
    // Outputs
    //**************************************************************************

    private static final int WINCH = 1;
    private static final int BRAKE = 1;
    private static final int CLUTCH = 1;

    private Jaguar winch = new Jaguar(WINCH);
    private Solenoid brake = new Solenoid(BRAKE);
    private Servo clutch = new Servo(CLUTCH);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public CarneyView(Carney _carney) {
        carney = _carney;
    }

    public void render() {
        winch.set(carney.getWinch());
        brake.set(carney.getBrake());
        clutch.set(carney.getClutch());
    }
}