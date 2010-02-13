package com.bhrobotics.widowmaker.scripted;

import java.util.Vector;

/**
 * Script instruction that gathers multiple instructions into a single
 * instruction wrapper. When this block is executed, each of the wrapped
 * instructions will be executed in turn.
 */
public class Block implements Instruction {

    private Vector script = new Vector();
    private int current = 0;

    /**
     * Create a new, empty, block instruction.
     */
    public Block() {
    }

    /**
     * Add an instruction to the script.
     * @param instruction the instruction to add
     */
    void add(Instruction instruction) {
        script.addElement(instruction);
    }

    /**
     * Called continuously while the script runner should be executing the
     * script. Generally, this call should execute the next instruction,
     * including any flow control that needs to be accomplished for a
     * subsequent call to this method.
     * @return true if the block is completed, false if it still has
     * instructions to execute
     */
    public boolean execute() {
        // Do the current step
        if (current < script.size()) {
            Instruction step = (Instruction) script.elementAt(current);
            if (step.execute()) {
                current++;
            }
        }
        // Check if we've finished all steps
        if(current >= script.size()) {
            current = 0;
            return true;
        } else {
            return false;
        }
    }
}
