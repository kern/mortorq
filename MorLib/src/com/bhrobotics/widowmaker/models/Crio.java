package com.bhrobotics.widowmaker.models;

import com.bhrobotics.morlib.Model;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Solenoid;

// Used for the dashboard view in order to show analog and digital IO data
public class Crio implements Model {

    //**************************************************************************
    // IO
    //**************************************************************************

    private static final int ANALOG_BUMPER = 1;
    private static final int SOLENOID_BUMPER = 2;
    private static final int MISC_SIDECAR = 4;
    private static final int MOTOR_SIDECAR = 8;

    private AnalogModule analog_bumper = AnalogModule.getInstance(ANALOG_BUMPER);
    private AnalogModule solenoid_bumper = AnalogModule.getInstance(SOLENOID_BUMPER);
    private DigitalModule misc_sidecar = DigitalModule.getInstance(MISC_SIDECAR);
    private DigitalModule motor_sidecar = DigitalModule.getInstance(MOTOR_SIDECAR);

    //**************************************************************************
    // Interface
    //**************************************************************************

    // The cRIO cannot be stopped! This is madness! This. is. ROBOTICSSSSSS!!!
    public void start() {}
    public void stop() {}

    //**************************************************************************
    // Getters
    //**************************************************************************

    // Analog bumpers
    public AnalogModule getAnalogBumper() { return analog_bumper; }
    public AnalogModule getSolenoidBumper() { return solenoid_bumper; }

    // Digital sidecars
    public DigitalModule getMiscSidecar() { return misc_sidecar; }
    public DigitalModule getMotorSidecar() { return motor_sidecar; }
    
    public byte getSolenoids() { return Solenoid.getAll(); }
}