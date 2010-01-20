package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.Joystick;

/**
* Handles the station used by robot operators, containing
* the joysticks, buttons, switches, etc., that are used to control
* the robot's actions in teleoperated mode, as well as the E-Stop button
* and other such controls.
**/
public class OperatorInterface extends Operator {
  
  private Joystick _driveStick;
  private Joystick _aimStick;
  
  OperatorInterface(Widowmaker robot) { super(robot); }
  
  void setDriveControl(Joystick stick) { _driveStick = stick; }
  void setAimControl(Joystick stick) { _aimStick = stick; }
  
  protected void continuous() {
    // Driving control
    notifyMove(_driveStick.getY(), _driveStick.getX(), _aimStick.getX());
    
    // TODO: Compressor control
    // TODO: Kicker control
  }
  
  protected void periodic() {}
}