package com.bhrobotics.widowmaker.view;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Servo;
import com.bhrobotics.widowmaker.model.carney.Carney;

// Controls Carney.
public class CarneyView {
    
    private static final int WINCH = 1;
    private static final int BRAKE = 1;
    private static final int CLUTCH = 1;

    private static Jaguar winch = new Jaguar(WINCH);
    private static Solenoid brake = new Solenoid(BRAKE);
    private static Servo clutch = new Servo(CLUTCH);

    public static void render(Carney carney) {
        winch.set(carney.getWinch());
        brake.set(carney.getBrake());
        clutch.set(carney.getClutch());
    }
}