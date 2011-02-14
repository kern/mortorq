package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.TimeoutEmitter;
import edu.wpi.first.wpilibj.DigitalInput;

class MorTorqControlListener extends ControlListener {
    // private TimeoutEmitter endGameTimeout             = new TimeoutEmitter();
    // private MorTorqTouchPanelFilter panelFilter       = new MorTorqTouchPanelFilter();
    // private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    // private MastListener mastListener                 = new MastListener();
    // private MinibotListener minibotListener           = new MinibotListener();
    // private ElbowListener elbowListener               = new ElbowListener();
    // private ClawListener clawListener                 = new ClawListener();
    // private WristListener wristListener               = new WristListener();
    // private CompressorListener compressorListener     = new CompressorListener();
    private DigitalInput sensor1  = new DigitalInput(4, 1);
    private DigitalInput sensor2  = new DigitalInput(4, 2);
    private DigitalInput sensor3  = new DigitalInput(4, 3);
    private DigitalInput sensor4  = new DigitalInput(4, 4);
    private DigitalInput sensor5  = new DigitalInput(4, 5);
    private DigitalInput sensor6  = new DigitalInput(4, 6);
    private DigitalInput sensor7  = new DigitalInput(4, 7);
    private DigitalInput sensor8  = new DigitalInput(4, 8);
    private DigitalInput sensor9  = new DigitalInput(4, 9);
    private DigitalInput sensor10 = new DigitalInput(4, 10);
    private DigitalInput sensor11 = new DigitalInput(4, 11);
    private DigitalInput sensor12 = new DigitalInput(4, 12);
    private DigitalInput sensor13 = new DigitalInput(4, 13);
    private DigitalInput sensor14 = new DigitalInput(4, 14);
    private DigitalInput sensor15 = new DigitalInput(4, 15);
    private DigitalInput sensor16 = new DigitalInput(4, 16);
    
    public void startAutonomous() {
        // mecanumDriveListener.stop();
        // mastListener.stop();
        // minibotListener.reset();
        // elbowListener.reset();
        // clawListener.reset();
        // wristListener.reset();
        // compressorListener.auto();
    }
    
    public void stopAutonomous() {
        // mecanumDriveListener.stop();
        // mastListener.stop();
    }
    
    public void startOperatorControl() {
        while (true) {
            System.out.println("Line sensor 1: " + sensor1.get());
            System.out.println("Line sensor 2: " + sensor2.get());
            System.out.println("Line sensor 3: " + sensor3.get());
            System.out.println("Line sensor 4: " + sensor4.get());
            System.out.println("Line sensor 5: " + sensor5.get());
            System.out.println("Line sensor 6: " + sensor6.get());
            System.out.println("Line sensor 7: " + sensor7.get());
            System.out.println("Line sensor 8: " + sensor8.get());
            System.out.println("Line sensor 9: " + sensor9.get());
            System.out.println("Line sensor 10: " + sensor10.get());
            System.out.println("Line sensor 11: " + sensor11.get());
            System.out.println("Line sensor 12: " + sensor12.get());
            System.out.println("Line sensor 13: " + sensor13.get());
            System.out.println("Line sensor 14: " + sensor14.get());
            System.out.println("Line sensor 15: " + sensor15.get());
            System.out.println("Line sensor 16: " + sensor16.get());
        }
        // joystickFilter.bind("all", panelFilter);
        // dsInputFilter.bind("all", panelFilter);
        
        // endGameTimeout.bind("startEndGame", minibotListener);
        // endGameTimeout.schedule("startEndGame", 110);
        
        // panelFilter.bind("all", mecanumDriveListener);
        // panelFilter.bind("all", mastListener);
        // panelFilter.bind("all", minibotListener);
        // panelFilter.bind("all", elbowListener);
        // panelFilter.bind("all", clawListener);
        // panelFilter.bind("all", wristListener);
        // panelFilter.bind("all", compressorListener);
    }
    
    public void stopOperatorControl() {
        // joystickFilter.unbind("all", panelFilter);
        // dsInputFilter.unbind("all", panelFilter);
        
        // endGameTimeout.unbind("startEndGame", minibotListener);
        // endGameTimeout.cancelAll();
        
        // panelFilter.unbind("all", mecanumDriveListener);
        // panelFilter.unbind("all", mastListener);
        // panelFilter.unbind("all", minibotListener);
        // panelFilter.unbind("all", elbowListener);
        // panelFilter.unbind("all", clawListener);
        // panelFilter.unbind("all", wristListener);
        // panelFilter.unbind("all", compressorListener);
    }
}