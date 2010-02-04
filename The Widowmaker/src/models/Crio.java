package com.bhrobotics.widowmaker.model;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Solenoid;

public class Crio {

    private static final int ANALOG_BUMPER = 1;
    private static final int SOLENOID_BUMPER = 2;
    private static final int MISC_SIDECAR = 4;
    private static final int MOTOR_SIDECAR = 8;

    private static AnalogModule analog_bumper = AnalogModule.getInstance(ANALOG_BUMPER);
    private static AnalogModule solenoid_bumper = AnalogModule.getInstance(SOLENOID_BUMPER);
    private static DigitalModule misc_sidecar = DigitalModule.getInstance(MISC_SIDECAR);
    private static DigitalModule motor_sidecar = DigitalModule.getInstance(MOTOR_SIDECAR);

    // Analog bumpers
    public static AnalogModule getAnalogBumper() { return analog_bumper; }
    public static AnalogModule getSolenoidBumper() { return solenoid_bumper; }

    // Digital sidecars
    public static DigitalModule getMiscSidecar() { return misc_sidecar; }
    public static DigitalModule getMotorSidecar() { return motor_sidecar; }

    // Solenoids
    public static byte getSolenoids() { return Solenoid.getAll(); }
}