package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;

public class MorTorqTouchPanelFilter extends TouchPanelFilter {
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
            // panel.trigger("stopMotors");
            // panel.trigger("mastStop");
            // panel.trigger("compressorStop");
        }
    }
    
    private class GameScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Game screen bound.");
            // panel.trigger("stopMotors");
            // panel.trigger("mastStop");
            // panel.trigger("compressorAuto");
        }
        
        public void handle(Event event) {
            
        }
    }
    
    private class DriveTrainScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Drive train screen bound.");
            // panel.trigger("stopMotors");
            // panel.trigger("mastStop");
            // panel.trigger("compressorStop");
        }
        
        public void handle(Event event) {
            
        }
    }
    
    private class ManipulatorsScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Manipulators screen bound.");
            // panel.trigger("stopMotors");
            // panel.trigger("mastStop");
            // panel.trigger("compressorManual");
        }
        
        public void handle(Event event) {
            
        }
    }
    
    private class MastScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Mast screen bound.");
            // panel.trigger("stopMotors");
            // panel.trigger("mastStop");
            // panel.trigger("compressorStop");
        }
        
        public void handle(Event event) {
            
        }
    }
}
