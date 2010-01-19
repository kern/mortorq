package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Watchdog;

/**
* The VM is configured to automatically run this class, and to call the
* functions corresponding to each mode, as described in the IterativeRobot
* documentation. If you change the name of this class or the package after
* creating this project, you must also update the manifest file in the resource
* directory.
**/
public class Widowmaker extends IterativeRobot {
  
  // Safety components
  private Watchdog watchdog = getWatchdog();
  private DigitalInput eStop;
  
  // Robot controllers
  private AiOperator ai;
  private OperatorInterface console;
  
  // Operator interface ports
  private static final int DRIVE_JOYSTICK = 1;
  private static final int AIM_JOYSTICK = 2;
  private static final int EMERGENCY_STOP = 2;
  
  // cRIO slots and channels
  private static final int RIGHT_FRONT_MOTOR_CHANNEL = 2;
  private static final int RIGHT_BACK_MOTOR_CHANNEL = 1;
  private static final int LEFT_FRONT_MOTOR_CHANNEL = 4;
  private static final int LEFT_BACK_MOTOR_CHANNEL = 5;
  private static final int MOTOR_SLOT = 4;
  
  /**
  * Runs on the initialization of the robot and sets up all of the various
  * IO of the robot.
  **/
  public void robotInit() {
    
    // The emergency stop button
    eStop = new DigitalInput(EMERGENCY_STOP);
    
    // Set up the autonomous mode controller
    ai = new AiOperator(this);
    
    // Set up the teleop mode controller (operator interface)
    console = new OperatorInterface(this);
    Joystick driveStick = new Joystick(DRIVE_JOYSTICK);
    Joystick aimStick = new Joystick(AIM_JOYSTICK);
    console.setDriveControl(driveStick);
    console.setAimControl(aimStick);
    
    // Set up the drive train component
    Jaguar rightFront = new Jaguar(MOTOR_SLOT, RIGHT_FRONT_MOTOR_CHANNEL);
    Jaguar rightBack = new Jaguar(MOTOR_SLOT, RIGHT_BACK_MOTOR_CHANNEL);
    Jaguar leftFront = new Jaguar(MOTOR_SLOT, LEFT_FRONT_MOTOR_CHANNEL);
    Jaguar leftBack = new Jaguar(MOTOR_SLOT, LEFT_BACK_MOTOR_CHANNEL);
    DriveTrain driveTrain = new DriveTrain(rightFront, rightBack,
                                           leftFront, leftBack);
    console.addListener(driveTrain);
    ai.addListener(driveTrain);
  }
  
  public void autonomousInit() {
    console.setActive(false);
    ai.setActive(true);
  }
  
  public void autonomousContinuous() {
    if(!eStop.get()) {
      ai.notifyEmergencyStop();
    }else{
      ai.continuous();
    }
  }
  
  public void autonomousPeriodic() {
    watchdog.feed();
    ai.periodic();
  }
  
  public void teleopInit() {
    ai.setActive(false);
    console.setActive(true);
  }
  
  public void teleopContinuous() {
    if(!eStop.get()) {
      console.notifyEmergencyStop();
    }else{
      console.continuous();
    }
  }
  
  public void teleopPeriodic() {
    watchdog.feed();
    console.periodic();
  }
}