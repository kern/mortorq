package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.DuplicateFilter;
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
            trigger("compressorStop");
            trigger("clawReset");
            trigger("wristReset");
            trigger("elbowReset");
            trigger("minibotReset");
        }
    }
    
    private class GameScreen extends TouchPanelScreen {
        private static final int MINIBOT = 10;
        private static final int CLAW    = 11;
        private static final int WRIST   = 12;
        private static final int ELBOW   = 13;
        
        public void bound() {
            System.out.println("[mortouch] Game screen bound.");
            trigger("stopMotors");
            trigger("compressorAuto");
            updateClaw(CLAW);
            updateWrist(WRIST);
            updateElbow(ELBOW);
            updateMinibotAuto(MINIBOT);
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.startsWith("changeJoystick")) {
                trigger(event);
            } else if (name.equals("changeDigital11")) {
                updateClaw(CLAW);
            } else if (name.equals("changeDigital12")) {
                updateWrist(WRIST);
            } else if (name.equals("changeDigital13")) {
                updateElbow(ELBOW);
            } else if (name.equals("changeDigital10")) {
                updateMinibotAuto(MINIBOT);
            }
        }
    }
    
    private class DriveTrainScreen extends TouchPanelScreen {
        private static final int LEFT_FRONT_STOP     = 4;
        private static final int LEFT_FRONT_BACKWARD = 5;
        private static final int LEFT_FRONT_SPEED    = 12;
        
        private static final int RIGHT_FRONT_STOP     = 6;
        private static final int RIGHT_FRONT_BACKWARD = 7;
        private static final int RIGHT_FRONT_SPEED    = 13;
        
        private static final int LEFT_BACK_STOP     = 8;
        private static final int LEFT_BACK_BACKWARD = 9;
        private static final int LEFT_BACK_SPEED    = 14;
        
        private static final int RIGHT_BACK_STOP     = 10;
        private static final int RIGHT_BACK_BACKWARD = 11;
        private static final int RIGHT_BACK_SPEED    = 15;
        
        public void bound() {
            System.out.println("[mortouch] Drive train screen bound.");
            
            updateLeftFront(LEFT_FRONT_STOP, LEFT_FRONT_BACKWARD, LEFT_FRONT_SPEED);
            updateRightFront(RIGHT_FRONT_STOP, RIGHT_FRONT_BACKWARD, RIGHT_FRONT_SPEED);
            updateLeftBack(LEFT_BACK_STOP, LEFT_BACK_BACKWARD, LEFT_BACK_SPEED);
            updateRightBack(RIGHT_BACK_STOP, RIGHT_BACK_BACKWARD, RIGHT_BACK_SPEED);
            
            trigger("compressorStop");
            trigger("clawReset");
            trigger("wristReset");
            trigger("elbowReset");
            trigger("minibotReset");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital4") || name.equals("changeDigital5") || name.equals("changeDigital12")) {
                updateLeftFront(LEFT_FRONT_STOP, LEFT_FRONT_BACKWARD, LEFT_FRONT_SPEED);
            } else if (name.equals("changeDigital6") || name.equals("changeDigital7") || name.equals("changeDigital13")) {
                updateRightFront(RIGHT_FRONT_STOP, RIGHT_FRONT_BACKWARD, RIGHT_FRONT_SPEED);
            } else if (name.equals("changeDigital8") || name.equals("changeDigital9") || name.equals("changeDigital14")) {
                updateLeftBack(LEFT_BACK_STOP, LEFT_BACK_BACKWARD, LEFT_BACK_SPEED);
            } else if (name.equals("changeDigital10") || name.equals("changeDigital11") || name.equals("changeDigital15")) {
                updateRightBack(RIGHT_BACK_STOP, RIGHT_BACK_BACKWARD, RIGHT_BACK_SPEED);
            }
        }
    }
    
    private class ManipulatorsScreen extends TouchPanelScreen {
        private static final int CLAW                    = 4;
        private static final int WRIST                   = 5;
        private static final int ELBOW                   = 6;
        private static final int COMPRESSOR_AUTO         = 7;
        private static final int COMPRESSOR_MANUAL_STATE = 8;
        private static final int MINIBOT                 = 9;
        
        public void bound() {
            System.out.println("[mortouch] Manipulators screen bound.");
            trigger("stopMotors");
            updateCompressorAuto(COMPRESSOR_AUTO);
            updateCompressorManualState(COMPRESSOR_MANUAL_STATE);
            updateClaw(CLAW);
            updateWrist(WRIST);
            updateElbow(ELBOW);
            updateMinibotManual(MINIBOT);
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital7")) {
                updateCompressorAuto(COMPRESSOR_AUTO);
            } else if (name.equals("changeDigital8")) {
                updateCompressorManualState(COMPRESSOR_MANUAL_STATE);
            } else if (name.equals("changeDigital4")) {
                updateClaw(CLAW);
            } else if (name.equals("changeDigital5")) {
                updateWrist(WRIST);
            } else if (name.equals("changeDigital6")) {
                updateElbow(ELBOW);
            } else if (name.equals("changeDigital9")) {
                updateMinibotManual(MINIBOT);
            }
        }
    }
    
    private class MastScreen extends TouchPanelScreen {
        public void bound() {
            System.out.println("[mortouch] Mast screen bound.");
            trigger("stopMotors");
            trigger("compressorStop");
            trigger("clawReset");
            trigger("wristReset");
            trigger("elbowReset");
            trigger("minibotReset");
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
    
    private void updateLeftFront(int stopChannel, int backwardChannel, int speedChannel) {
        updateMotor("LeftFront", stopChannel, backwardChannel, speedChannel);
    }
    
    private void updateRightFront(int stopChannel, int backwardChannel, int speedChannel) {
        updateMotor("RightFront", stopChannel, backwardChannel, speedChannel);
    }
    
    private void updateLeftBack(int stopChannel, int backwardChannel, int speedChannel) {
        updateMotor("LeftBack", stopChannel, backwardChannel, speedChannel);
    }
    
    private void updateRightBack(int stopChannel, int backwardChannel, int speedChannel) {
        updateMotor("RightBack", stopChannel, backwardChannel, speedChannel);
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
    
    private void updateCompressorAuto(int channel) {
        if (getDigital(channel)) {
            trigger("compressorAuto");
        } else {
            trigger("compressorManual");
        }
    }
    
    private void updateCompressorManualState(int channel) {
        if (getDigital(channel)) {
            trigger("compressorManualOn");
        } else {
            trigger("compressorManualOff");
        }
    }
    
    private void updateClaw(int channel) {
        if (getDigital(channel)) {
            trigger("clawNarrow");
        } else {
            trigger("clawWide");
        }
    }
    
    private void updateWrist(int channel) {
        if (getDigital(channel)) {
            trigger("wristLower");
        } else {
            trigger("wristRaise");
        }
    }
    
    private void updateElbow(int channel) {
        if (getDigital(channel)) {
            trigger("elbowLower");
        } else {
            trigger("elbowRaise");
        }
    }
    
    private void updateMinibotAuto(int channel) {
        if (getDigital(channel)) {
            trigger("disengageSafety");
        } else {
            trigger("engageSafety");
        }
    }
    
    private void updateMinibotManual(int channel) {
        if (getDigital(channel)) {
            trigger("minibotDeploy");
        } else {
            trigger("minibotRedact");
        }
    }
}