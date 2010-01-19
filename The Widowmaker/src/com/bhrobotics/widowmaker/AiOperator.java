package com.bhrobotics.widowmaker;

/**
* Class that controls the robot while in autonomous mode.
* @author BHRobotics
*/
class AiOperator extends Operator {
  
  // Time interval (microseconds) between calls to perioidic()
  private static final double PERIOD = 10000.0;
  
  AiOperator(Widowmaker robot) {
    super(robot);
  }
  
  /**
  * @return the desired time interval (in microseconds, not milliseconds)
  * between calls to periodic()
  */
  protected double getPeriod() {
    return PERIOD;
  }
  
  /**
   * Called periodically while this operator is in control.
   */
  protected void periodic() {}
}