package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import java.util.Hashtable;

public class MorTorqTouchPanelFilter extends TouchPanelFilter {
    DriverStationEnhancedIO ds = DriverStation.getInstance().getEnhancedIO();
    
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
        private static final int LEFT_FRONT_STOP = 4;
        private static final int LEFT_FRONT_BACKWARD = 5;
        private static final int LEFT_FRONT_SPEED = 12;
        
        private static final int RIGHT_FRONT_STOP = 6;
        private static final int RIGHT_FRONT_BACKWARD = 7;
        private static final int RIGHT_FRONT_SPEED = 13;
        
        private static final int LEFT_BACK_STOP = 8;
        private static final int LEFT_BACK_BACKWARD = 9;
        private static final int LEFT_BACK_SPEED = 14;
        
        private static final int RIGHT_BACK_STOP = 10;
        private static final int RIGHT_BACK_BACKWARD = 11;
        private static final int RIGHT_BACK_SPEED = 15;
        
        public void bound() {
            System.out.println("[mortouch] Drive train screen bound.");
            
            updateLeftFront();
            updateRightFront();
            updateLeftBack();
            updateRightBack();
            
            // trigger("mastStop");
            // trigger("compressorStop");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital4") || name.equals("changeDigital5") || name.equals("changeDigital12")) {
                updateLeftFront();
            }
            
            if (name.equals("changeDigital6") || name.equals("changeDigital7") || name.equals("changeDigital13")) {
                updateRightFront();
            }
            
            if (name.equals("changeDigital8") || name.equals("changeDigital9") || name.equals("changeDigital14")) {
                updateLeftBack();
            }
            
            if (name.equals("changeDigital10") || name.equals("changeDigital11") || name.equals("changeDigital15")) {
                updateRightBack();
            }
        }
        
        private void updateLeftFront() {
            updateMotor("LeftFront", LEFT_FRONT_STOP, LEFT_FRONT_BACKWARD, LEFT_FRONT_SPEED);
        }
        
        private void updateRightFront() {
            updateMotor("RightFront", RIGHT_FRONT_STOP, RIGHT_FRONT_BACKWARD, RIGHT_FRONT_SPEED);
        }
        
        private void updateLeftBack() {
            updateMotor("LeftBack", LEFT_BACK_STOP, LEFT_BACK_BACKWARD, LEFT_BACK_SPEED);
        }
        
        private void updateRightBack() {
            updateMotor("RightBack", RIGHT_BACK_STOP, RIGHT_BACK_BACKWARD, RIGHT_BACK_SPEED);
        }
        
        private void updateMotor(String name, int stopChannel, int backwardChannel, int speedChannel) {
            double value;
            
            if (getDigital(backwardChannel)) {
                value = 1.0;
            } else if (getDigital(stopChannel)) {
                value = 0.0;
            } else {
                value = -1.0;
            }
            
            if (!getDigital(speedChannel)) {
                value /= 2.0;
            }
            
            Hashtable data = new Hashtable();
            data.put("value", new Double(value));
            
            trigger("changeMotor" + name, data);
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
    
    private boolean getDigital(int channel) {
        try {
            return !ds.getDigital(channel);
        } catch (DriverStationEnhancedIO.EnhancedIOException e) {
            return false;
        }
    }
}