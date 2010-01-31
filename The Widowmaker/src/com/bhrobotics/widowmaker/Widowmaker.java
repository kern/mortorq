package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Watchdog;

// The VM is configured to automatically run this class, and to call the
// functions corresponding to each mode, as described in the IterativeRobot
// documentation.
public class Widowmaker extends IterativeRobot {

    // Time interval (microseconds) between calls to perioidic()
    private static final double PERIOD = 10000.0;

    private Watchdog watchdog = getWatchdog();
    private AutoController autoController;
    private TeleopController teleopController;

    public void robotInit() {
        autoController = new AutoController();
        teleopController = new TeleopController();

        // Time interval (microseconds) between calls to perioidic()
        setPeriod(PERIOD);
    }

    // Autonomous
    public void autonomousContinuous() {
        autoController.run_continuous();
    }

    public void autonomousPeriodic() {
        watchdog.feed();
        autoController.run_periodic();
    }

    // Teleop
    public void teleopContinuous() {
        teleopController.run_continuous();
    }

    public void teleopPeriodic() {
        watchdog.feed();
        teleopController.run_periodic();
    }
}