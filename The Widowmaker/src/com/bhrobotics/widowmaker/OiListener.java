package com.bhrobotics.widowmaker;

public interface OiListener {
  
  public void emergencyStop();
  
  /**
  * User has changed the driving controls to a new speed, strafe,
  * or rotation. The speed gives the desired forward velocity of the
  * robot, with zero indicating stopped and negative values indicating
  * driving in reverse. The strafe gives the desired sideways velocity
  * of the  robot, with zero indicating stopped and negative values
  * indication driving to the left. The rotation gives the desired angular
  * velocity of the robot, with zero indicating no turn, positive values
  * indicating a clockwise (right) turn, and negative values indicating a
  * counterclockwise (left) turn.
  *
  * @param speed the new speed, in range [-1..1]
  * @param strafe the new strafe, in range [-1..1]
  * @param rotation the new rotation, in range [-1..1]
  **/
  public void move(double speed, double strafe, double rotation);
}
