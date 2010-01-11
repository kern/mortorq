/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Widowmaker extends IterativeRobot {
    private DriveTrain driveTrain;
    private OperatorInterface console;
    private AiOperator ai;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        console = new OperatorInterface(this);
        ai = new AiOperator(this);

        driveTrain = new DriveTrain();
        console.addListener(driveTrain);
        ai.addListener(driveTrain);
    }


    /**
     * Called when we first enter autonomous mode
     */
    public void autonomousInit() {
        console.setActive(false);
        ai.setActive(true);
    }


    /**
     * Called continuously during autonomous mode
     */
    public void autonomousContinuous() {
        ai.continuous();
    }


    /**
     * Called periodically during autonomous mode
     */
    public void autonomousPeriodic() {
        ai.periodic();
    }


    /**
     * Called when we first enter teleoperated mode
     */
    public void teleopInit() {
        ai.setActive(false);
        console.setActive(true);
    }


    /**
     * Called continuously during teleoperated mode
     */
    public void teleopContinuous() {
        console.continuous();
    }


    /**
     * Called periodically during teleoperated mode
     */
    public void teleopPeriodic() {
        console.periodic();
    }
}
