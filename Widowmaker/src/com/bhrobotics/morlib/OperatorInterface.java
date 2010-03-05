package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    protected DriverStationEnhancedIO ds = DriverStation.getInstance().getEnhancedIO();
    protected Joystick joystick1;
    protected Joystick joystick2;
    protected Joystick joystick3;
    protected Joystick joystick4;
    protected int screen = 1;

    //**************************************************************************
    // Automation
    //**************************************************************************

    public OperatorInterface() {
        joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
        joystick3 = new Joystick(3);
        joystick4 = new Joystick(4);
    }

    // The screen number is the binary representation of the first three digital
    // inputs. There can be a total of 8 different screens, starting at 0. Most
    // significant bit is the first digital input.
    public void refresh() {
        // If no bit is on, that means it's screen 1.
        screen = 1;
        
        try {
            if (!ds.getDigital(1)) { screen += 4; }
            if (!ds.getDigital(2)) { screen += 2; }
            if (!ds.getDigital(3)) { screen += 1; }
        }catch(EnhancedIOException e) {}
    }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public boolean joyButton(int button) {
        if(joystick1.getRawButton(button)) { return true; }
        return false;
    }

    public boolean inputOn(int in) {
        try {
            return !ds.getDigital(in);
        }catch(EnhancedIOException e) {
            return false;
        }
    }

    public boolean inputOn(int _screen, int in) {
        return screen == _screen && inputOn(in);
    }

    public boolean inputOff(int in) { return !inputOn(in); }
    public boolean inputOff(int _screen, int in) {
        return screen == _screen && inputOff(in);
    }

    public boolean getStopped() { return screen == 1; }
}