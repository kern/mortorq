package com.bhrobotics.widowmaker.scripted;

/**
 * Interface for script instructions.
 */
public interface Instruction {
    /**
     * Called when the instruction executes.
     * @return "completed": false if this instruction has not finished and needs
     * to be called again on the next cycle; true if the instruction is done
     * and should not be called again in the absence of looping control
     * structures.
     */
    public boolean execute();
}
