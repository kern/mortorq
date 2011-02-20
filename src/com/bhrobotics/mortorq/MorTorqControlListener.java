package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.ControlListener;
import com.bhrobotics.morlib.DashboardListener;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.TimeoutEmitter;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import org.thecatattack.system451.communication.dashboard.ZomBDashboard;
import org.thecatattack.system451.communication.dashboard.ZomBModes;

class MorTorqControlListener extends ControlListener {
    private LineTrackerFilter lineTrackerFilter       = new LineTrackerFilter();
    private TimeoutEmitter endGameTimeout             = new TimeoutEmitter();
    private MorTorqTouchPanelFilter panelFilter       = new MorTorqTouchPanelFilter();
    private MecanumDriveListener mecanumDriveListener = new MecanumDriveListener();
    private CompressorListener compressorListener     = new CompressorListener();
    private ClawListener clawListener                 = new ClawListener();
    private WristListener wristListener               = new WristListener();
    private ElbowListener elbowListener               = new ElbowListener();
    private MinibotListener minibotListener           = new MinibotListener();
    private MastListener mastListener                 = new MastListener();
    private ZomBDashboard zomB                        = ZomBDashboard.getInstance(ZomBModes.TCP, true);
    
    public void start() {
        // Initialize the camera.
        // AxisCamera camera = AxisCamera.getInstance();
        // camera.writeResolution(AxisCamera.ResolutionT.k160x120);
        // camera.writeCompression(30);
        // camera.writeMaxFPS(1);
        
        process.bind("tick", mastListener);
        process.bind("tick", compressorListener);
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
    
    public void startOperatorControl() {
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
