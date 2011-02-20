package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.TimeoutEmitter;
import com.bhrobotics.morlib.DashboardListener;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.camera.AxisCamera;

class MorTorqControlListener extends ControlListener {
    private DashboardListener dashboardListener       = new DashboardListener();
    private TimeoutEmitter endGameTimeout             = new TimeoutEmitter();
    private MorTorqTouchPanelFilter panelFilter       = new MorTorqTouchPanelFilter();
    private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    private CompressorListener compressorListener     = new CompressorListener();
    private ClawListener clawListener                 = new ClawListener();
    private WristListener wristListener               = new WristListener();
    private ElbowListener elbowListener               = new ElbowListener();
    private MinibotListener minibotListener           = new MinibotListener();
    private MastListener mastListener                 = new MastListener();
    
    public void start() {
        // Initialize the camera.
        // AxisCamera camera = AxisCamera.getInstance();
        // camera.writeResolution(AxisCamera.ResolutionT.k160x120);
        // camera.writeCompression(30);
        // camera.writeMaxFPS(1);
        
        process.bind("newDataAvailable", dashboardListener);
    }
    
    public void startAutonomous() {
        mecanumDriveListener.stop();
        compressorListener.auto();
        clawListener.reset();
        wristListener.reset();
        elbowListener.reset();
        minibotListener.reset();
        mastListener.stop();
    }
    
    public void stopAutonomous() {
        mecanumDriveListener.stop();
        mastListener.stop();
    }
    
    public void startOperatorControl() {
        process.bind("tick", mecanumDriveListener);
        process.bind("tick", compressorListener);
        process.bind("tick", mastListener);
        
        joystickFilter.bind("changeJoystick1", panelFilter);
        dsFilter.bind("all", panelFilter);
        dsFilter.update(true);
        
        endGameTimeout.bind("startEndGame", minibotListener);
        endGameTimeout.schedule("startEndGame", 110);
        
        panelFilter.bind("all", mecanumDriveListener);
        panelFilter.bind("all", compressorListener);
        panelFilter.bind("all", clawListener);
        panelFilter.bind("all", wristListener);
        panelFilter.bind("all", elbowListener);
        panelFilter.bind("all", minibotListener);
        panelFilter.bind("all", mastListener);
    }
    
    public void stopOperatorControl() {
        process.unbind("tick", mecanumDriveListener);
        process.unbind("tick", compressorListener);
        process.unbind("tick", mastListener);
        
        joystickFilter.unbind("changeJoystick1", panelFilter);
        dsFilter.unbind("all", panelFilter);
        
        endGameTimeout.unbind("startEndGame", minibotListener);
        endGameTimeout.cancelAll();
        
        panelFilter.unbind("all", mecanumDriveListener);
        panelFilter.unbind("all", compressorListener);
        panelFilter.unbind("all", clawListener);
        panelFilter.unbind("all", wristListener);
        panelFilter.unbind("all", elbowListener);
        panelFilter.unbind("all", minibotListener);
        panelFilter.unbind("all", mastListener);
    }
}