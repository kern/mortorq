package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.DriverStation;

public class MorTorqTouchPanelFilter extends TouchPanelFilter {
    DriverStation ds = DriverStation.getInstance();
    
    private StopScreen stopScreen                 = new StopScreen();
    private GameScreen gameScreen                 = new GameScreen();
    private DriveTrainScreen driveTrainScreen     = new DriveTrainScreen();
    private ManipulatorsScreen manipulatorsScreen = new ManipulatorsScreen();
    private MastScreen mastScreen                 = new MastScreen();
    
    public MorTorqTouchPanelFilter() {
        setScreen(0, new StopScreen());
        setScreen(1, new GameScreen());
        setScreen(2, new DriveTrainScreen());
        setScreen(3, new ManipulatorsScreen());
        setScreen(4, new MastScreen());
    }
    
    private class StopScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Stop screen bound.");
            trigger("stopMotors");
            // trigger("mastStop");
            // trigger("compressorStop");
        }
    }
    
    private class GameScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Game screen bound.");
            trigger("stopMotors");
            // trigger("mastStop");
            // trigger("compressorAuto");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.startsWith("changeJoystick")) {
                trigger(event);
            }
        }
    }
    
    private class DriveTrainScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Drive train screen bound.");
            trigger("stopMotors");
            // trigger("mastStop");
            // trigger("compressorStop");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital4")) {
                System.out.println("[debug] Handling: " + name);
            }
            
            if (name.equals("changeDigital4") || name.equals("changeDigital5")) {
                boolean digital4 = ds.getDigitalIn(4);
                boolean digital5 = ds.getDigitalIn(5);
                
                if (!digital4 && digital5) {
                    System.out.println("[mecanum] Stopping front left.");
                } else if (digital4 && digital5) {
                    System.out.println("[mecanum] Forward front left.");
                } else {
                    System.out.println("[mecanum] Backward front left.");
                }
            }
        }
    }
    
    private class ManipulatorsScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Manipulators screen bound.");
            trigger("stopMotors");
            // trigger("mastStop");
            // trigger("compressorManual");
        }
        
        public void handle(Event event) {
            
        }
    }
    
    private class MastScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Mast screen bound.");
            panel.trigger("stopMotors");
            // panel.trigger("mastStop");
            // panel.trigger("compressorStop");
        }
        
        public void handle(Event event) {
            
        }
    }
}