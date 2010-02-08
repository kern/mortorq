package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;

public class OperatorInterface {

    private DriverStation ds = DriverStation.getInstance();

    private Joystick joystick_1 = new Joystick(1);
    private Joystick joystick_2 = new Joystick(2);
    private Joystick joystick_3 = new Joystick(3);
    private Joystick joystick_4 = new Joystick(4);

    private boolean newData = false;

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

    public boolean getDigitalIn(int number) {
        return ds.getDigitalIn(number);
    }

    public boolean getNewData() { return newData; }
    public void setNewData(boolean _newData) { newData = _newData; }
}