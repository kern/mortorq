package com.bhrobotics.widowmaker.model;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.Solenoid;

public class Crio {
    
    private static final int ANALOG_BUMPER = 1;
    private static final int SOLENOID_BUMPER = 2;
    private static final int MISC_SIDECAR = 4;
    private static final int MOTOR_SIDECAR = 8;

    private AnalogModule analog_bumper;
    private AnalogModule solenoid_bumper;
    private DigitalModule misc_sidecar;
    private DigitalModule motor_sidecar;

    private static Crio instance = new Crio();

    public Crio() {
        analog_bumper = AnalogModule.getInstance(ANALOG_BUMPER);
        solenoid_bumper = AnalogModule.getInstance(SOLENOID_BUMPER);
        misc_sidecar = DigitalModule.getInstance(MISC_SIDECAR);
        motor_sidecar = DigitalModule.getInstance(MOTOR_SIDECAR);
    }

    public static Crio getInstance() { return Crio.instance; }

    public Dashboard getDashboardPackerLow() {
        return DriverStation.getInstance().getDashboardPackerLow();
    }

    // Analog bumpers
    public AnalogModule getAnalogBumper() { return analog_bumper; }
    public AnalogModule getSolenoidBumper() { return solenoid_bumper; }

    // Digital sidecars
    public DigitalModule getMiscSidecar() { return misc_sidecar; }
    public DigitalModule getMotorSidecar() { return motor_sidecar; }

    // Solenoids
    public byte getSolenoids() { return Solenoid.getAll(); }
}