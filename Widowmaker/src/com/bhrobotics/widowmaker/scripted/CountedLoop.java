package com.bhrobotics.widowmaker.scripted;

/**
 * Script instruction that executes another instruction a configurable number
 * of times.
 */
public class CountedLoop implements Instruction {

    private Instruction step;
    private int limit;
    private int current = 0;

    /**
     * Create a new counted loop instruction. The given instruction will be
     * executed _count times. Note that a Block instruction may be used to
     * execute multiple instructions in the body of this loop.
     * @param _count the number of times to execute the body
     * @param _step the body of the loop
     */
    public CountedLoop(int _count, Instruction _step) {
        limit = _count;
        step = _step;
    }

    /**
     * @return true if the instruction has been executed count times, false
     * otherwise
     */
    public boolean execute() {
        if (step.execute()) {
            current--;
        }
        if (current == 0) {
            current = limit;
            return true;
        } else {
            return false;
        }
    }
}
