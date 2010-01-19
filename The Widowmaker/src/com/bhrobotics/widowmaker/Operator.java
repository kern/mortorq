package com.bhrobotics.widowmaker;

import java.util.Enumeration;
import java.util.Vector;

/**
* Abstract base class for operators. Expected concrete subclasses include
* a class for an operator console, where a human uses various physical
* controls (switches, joysticks, etc.) to control the robot, and a class
* for an autonomous controller, in which software controls the robot without
* human intervention.
**/
public class Operator {
  
  // Time interval (microseconds) between calls to perioidic()
  private static final double PERIOD = 10000.0;
  
  // True if and only if this operator is in control of the robot
  private boolean _active = false;
  private Widowmaker _robot;
  private Vector _listeners = new Vector();
  
  public Operator(Widowmaker robot) { _robot = robot; }
  
  /**
  * @return the desired time interval (in microseconds, not milliseconds)
  * between calls to periodic()
  **/
  protected double getPeriod() { return PERIOD; };
  
  /**
  * Called continuously while this operator is in control. Must be
  * overridden to use this method.
  */
  protected void continuous() {}
  
  /**
  * Called periodically while this operator is in control. Must be
  * overridden to use this method.
  */
  protected void periodic() {}
  
  /**
  * Sets whether this operator is in control of the robot.
  * When set to false, this operator should issue no events to the
  * listeners, with the potential exception of an emergency stop.
  * @param enable true to enable control, false to disable control
  **/
  public void setActive(boolean enable) {
    _active = enable;
    if(_active) {
      _robot.setPeriod(getPeriod());
    }
  }
  
  /**
  * @return true if and only if this operator is in control of the robot
  **/
  public boolean isActive() { return _active; }
  
  /**
  * Add a listener to the operator. The listener mechanism is used to
  * send control information to the various robot systems (such as the
  * drive train).
  * @param listener the listener to add; once added, the listener will
  * receive control events
  **/
  public void addListener(OiListener listener) {
    _listeners.addElement(listener);
  }
  
  /**
  * Notify all listeners that an emergency stop has been requested.
  **/
  protected void notifyEmergencyStop() {
    Enumeration en = _listeners.elements();
    while(en.hasMoreElements()) {
      OiListener l = (OiListener) en.nextElement();
      l.emergencyStop();
    }
  }
  
  /**
  * Notify all listeners that movement should change.
  * @param speed the new movement speed
  * @param strafe the new strafe
  * @param rotation the new rotation
  **/
  protected void notifyMove(double speed, double strafe, double rotation) {
    Enumeration en = _listeners.elements();
    while(en.hasMoreElements()) {
      OiListener l = (OiListener) en.nextElement();
      l.move(speed, strafe, rotation);
    }
  }
}