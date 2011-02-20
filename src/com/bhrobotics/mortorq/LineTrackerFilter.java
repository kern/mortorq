package com.bhrobotics.mortorq;

import java.util.Hashtable;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.Filter;
import edu.wpi.first.wpilibj.DigitalInput;


public class LineTrackerFilter extends Filter {
    private static final int LINE_TRACKER_SLOT = 6;
    private static final int LINE_A_CHANNEL = 1; 
    private static final int LINE_B_CHANNEL = 2; 
    private static final int LINE_C_CHANNEL = 3;
    
    private static final double FOWARD_X = 1; 
    private static final double FOWARD_Y = 1; 
    private static final double FOWARD_ROTATION = 1; 
    private static final double RIGHT_X = 1; 
    private static final double RIGHT_Y = 1; 
    private static final double RIGHT_ROTATION = 1; 
    private static final double LEFT_X = 1; 
    private static final double LEFT_Y = 1; 
    private static final double LEFT_ROTATION = 1;
    
    private Hashtable fowardData = new Hashtable();
    private Hashtable rightData = new Hashtable();
    private Hashtable leftData = new Hashtable();
     
    private DigitalInput sensorL = new DigitalInput (LINE_TRACKER_SLOT,LINE_A_CHANNEL); 
    private DigitalInput sensorC = new DigitalInput (LINE_TRACKER_SLOT,LINE_B_CHANNEL); 
    private DigitalInput sensorR = new DigitalInput (LINE_TRACKER_SLOT,LINE_C_CHANNEL);
    
    public Filter() {
        fowardData.add("x",FOWARD_X);
        fowardData.add("x",FOWARD_Y);
        fowardData.add("rotation",FOWARD_ROTATION);
        rightData.add("x",RIGHT_X);
        rightData.add("y",RIGHT_Y);
        rightData.add("rotation",FOWARD_ROTATION);
        leftData.add("x",LEFT_X);
        leftData.add("y",LEFT_Y);
        leftData.add("rotation",FOWARD_ROTATION);
    }

    public void handle(Event event) {
        if (sensorA.get() && sensorB.get() && sensorB.get()) {
            stop();
        } else if (sensorL.get()) {
            right();
        } else if (sensorC.get()) {
            foward();
        } else if (sensorR.get()) {
            left();
        } else {
            stop();
        }
    }

    public void bound (EventEmitter em, String name) {}
    public void unbound (EventEmitter em, String name) {}

    public void left() {
        trigger("drive",leftData);
    }

    public void foward() {
        trigger("drive",fowardData);
    }

    public void right() {
        trigger("drive",rightData);
    }

    public void stop() {
        trigger(new Event("stopMotors")
    }
}