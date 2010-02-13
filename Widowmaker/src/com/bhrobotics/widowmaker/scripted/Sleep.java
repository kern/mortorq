package com.bhrobotics.widowmaker.scripted;

/**
 * Instruction to delay a script for a certain time.
 */
public class Sleep implements Instruction {

    private double timeInSec;
    private double endTime = -1.0;

    /**
     * Create an instruction that causes the script to delay execution for
     * the given number of seconds.
     * @param _timeInSec the delay time in seconds
     */
    public Sleep(double _timeInSec) {
        if (_timeInSec <= 0.0) {
            throw new IllegalArgumentException(
                    "time (" + _timeInSec + ") must be > 0");
        }
        timeInSec = _timeInSec;
    }

    /**
     * @return true if and only if the sleep time is over
     */
    public boolean execute() {
        if (endTime < 0) {
            endTime = System.currentTimeMillis() + (1000.0 * timeInSec);
            return false;
        } else if (endTime > System.currentTimeMillis()) {
            endTime = -1.0;
            return true;
        } else {
            return false;
        }
    }
}
