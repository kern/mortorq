package com.bhrobotics.widowmaker.scripted;

import com.bhrobotics.widowmaker.models.DriveTrain;

/**
 * Instruction to set the drive train to a particular motion.
 */
public class SetDrive implements Instruction {

    private DriveTrain drive;
    private double x;
    private double y;
    private double rotation;

    /**
     * Create an instruction that will set the drive to the given values. All
     * velocities are given in the range [1.0..-1.0], with 0.0 meaning stopped
     * in that particular direction, and "-x" meaning "equal in magnitude but
     * opposite in direction to x".
     * @param _drive the drive train to set
     * @param _x the desired forward velocity (1.0 = full speed forward,
     * 0.5 = half speed forward, 0.0 = no forward speed, -1.0 = full speed
     * reverse, etc.)
     * @param _y the desired sideways (strafe) velocity (1.0 = full speed to
     * the right, 0.5 = half speed to the right, 0.0 = no sideways speed,
     * -1.0 = full speed to the left).
     * @param _rotation the desired angular (turning) velocity (1.0 = full
     * speed turning right, -1.0 = full speed turning left, 0.0 = no turning)
     */
    public SetDrive(DriveTrain _drive, double _x, double _y, double _rotation) {
        drive = _drive;
        x = _x;
        y = _y;
        rotation = _rotation;
    }

    /**
     * Set the drive train's desired velocities
     * @return true, as this instruction always completes immediately
     */
    public boolean execute() {
        drive.mecanum(x, y, rotation);
        return true;
    }

}
