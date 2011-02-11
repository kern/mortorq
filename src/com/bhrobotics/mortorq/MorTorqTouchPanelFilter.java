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
    }
    
    private class StopScreen extends TouchPanelScreen {
        public void handle(Event event) {
            
        }
    }
    
    private class GameScreen extends TouchPanelScreen {
        public void handle(Event event) {
            
        }
    }
    
    private class DriveTrainScreen extends TouchPanelScreen {
        public void handle(Event event) {
            
        }
    }
    
    private class ManipulatorsScreen extends TouchPanelScreen {
        public void handle(Event event) {
            
        }
    }
    
    private class MastScreen extends TouchPanelScreen {
        public void handle(Event event) {
            
        }
    }
}
