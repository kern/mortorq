package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    private DriverStation ds = DriverStation.getInstance();
    private int screen = 1;

    //**************************************************************************
    // Joysticks
    //**************************************************************************

    private Joystick joystick_1 = new Joystick(1);
    private Joystick joystick_2 = new Joystick(2);
    private Joystick joystick_3 = new Joystick(3);
    private Joystick joystick_4 = new Joystick(4);
    //**************************************************************************
    // Screens
    //**************************************************************************

    private static final int STOPPED = 1;
    private static final int DRIVE_TRAIN = 2;
    private static final int PNEUMATICS = 4;
    private static final int GAME = 5;
    private static final int DEMO_DRIVE = 7;
    private static final int DEMO_PNEUMATICS = 8;

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

    public Joystick getJoystick(int number) {
        if(number == 1) {
            return joystick_1;
        }else if(number == 2) {
            return joystick_2;
        }else if(number == 3) {
            return joystick_3;
        }else{
            return joystick_4;
        }
    }
    
    // Screen 0 means the robot's stopped, even if we're not actually on it.
    public boolean getStopped() { return screen == 0; }
}