package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    private DriverStation ds = DriverStation.getInstance();
    protected Joystick[] joysticks;
    protected int screen = 1;

    //**************************************************************************
    // Automation
    //**************************************************************************

    public OperatorInterface() {
        for(int i = 0; i < 3; i++) {
            joysticks[i] = new Joystick(i+1);
        }
    }

    // The screen number is the binary representation of the first three digital
    // inputs. There can be a total of 8 different screens, starting at 0. Most
    // significant bit is the first digital input.
    public void refresh() {

        // If no bit is on, that means it's screen 1.
        screen = 1;

        // Add the bits if they're on.
        if(ds.getDigitalIn(1)) { screen += 4; }
        if(ds.getDigitalIn(2)) { screen += 2; }
        if(ds.getDigitalIn(3)) { screen += 1; }
    }

    //**************************************************************************
    // Getters
    //**************************************************************************

    public boolean joyButton(int button) {
        for(int i = 0; i < 3; i++) {
            if(joysticks[i].getRawButton(button)) { return true; }
        }
        return false;
    }

    public boolean inputOn(int in) { return ds.getDigitalIn(in); }
    public boolean inputOn(int _screen, int in) {
        return screen == _screen && inputOn(in);
    }

    public boolean inputOff(int in) { return !inputOn(in); }
    public boolean inputOff(int _screen, int in) {
        return screen == _screen && inputOff(in);
    }

    public boolean getStopped() { return screen == 0; }
}