package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;

public class EncoderTestListener extends Listener {
    private static final int SLOT   = 6;
    private static final int SIDE_A = 1;
    private static final int SIDE_B = 2;
    private static final int MOTOR  = 3;
    
    private static final double KP = 1.0;
    private static final double KI = 0.0;
    private static final double KD = 0.0;
    
    private RateEncoder encoder = new RateEncoder(SLOT, SIDE_A, SLOT, SIDE_B, false);
    private PIDJaguar motor     = new PIDJaguar(SLOT, MOTOR);
    private PIDController pid   = new PIDController(KP, KI, KD, encoder, motor);
    
    public EncoderTestListener() {
        encoder.setDistancePerPulse(0.05 / 360);
        encoder.start();
        
        pid.setInputRange(-1.0, 1.0);
        pid.enable();
    }
    
    public void handle(Event event) {
        Joystick joystick = (Joystick) event.getData("joystick");
        pid.setSetpoint(joystick.getX());
    }
}