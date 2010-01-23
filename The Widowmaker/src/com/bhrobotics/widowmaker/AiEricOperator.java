/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bhrobotics.widowmaker;

/**
 *
 * @author Developer
 * Need to impliment code to:
 * controll kicker
 * camera (if used)
 * complicated driving
 */

public class AiEricOperator extends Operator {

    private static final double PERIOD = 10000.0;

    public AiEricOperator(Widowmaker robot) {
        super(robot);
    }

    protected double getPeriod() {
        return PERIOD;
    }

    //@Override
    protected void continuous() {
    }


    protected void linear() {
        notifyMove(0.75, 0, 0);
        waitUntilBallSensed();
        notifyMove(0, 0, 0);

    }

    private void waitUntilBallSensed() {
    }
}