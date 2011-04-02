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
            Claw.getInstance().wide();
            Wrist.getInstance().lower();
            Elbow.getInstance().raise();
            Minibot.getInstance().retract();
            
            trigger("mastStop");
            trigger("mastEncoderOverrideOff");
            trigger("mastFast");
        }
    }
    
    private class GameScreen extends TouchPanelScreen {
        private static final int MAST_BIT_1 = 4;
        private static final int MAST_BIT_2 = 5;
        private static final int MAST_BIT_3 = 6;
        private static final int POSITION   = 7;
        private static final int UP_ARROW   = 8;
        private static final int DOWN_ARROW = 9;
        private static final int MINIBOT    = 10;
        private static final int CLAW       = 11;
        private static final int WRIST      = 12;
        private static final int ELBOW      = 13;
        private static final int NONE       = 14;
        
        public void bound() {
            System.out.println("[mortouch] Game screen bound.");
            
            trigger("stopMotors");
            
            trigger("compressorAuto");
            claw(CLAW);
            wrist(WRIST);
            elbow(ELBOW, true);
            Minibot.getInstance().retract();
            minibotInterlock(MINIBOT);
            
            mast(POSITION, MAST_BIT_1, MAST_BIT_2, MAST_BIT_3, UP_ARROW, DOWN_ARROW, NONE);
            trigger("mastEncoderOverrideOff");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.startsWith("changeJoystick")) {
                trigger(event);
            } else if (name.equals("changeDigital4") || name.equals("changeDigital5") || name.equals("changeDigital6") || name.equals("changeDigital7") || name.equals("changeDigital8") || name.equals("changeDigital9") || name.equals("changeDigital14")) {
                mast(POSITION, MAST_BIT_1, MAST_BIT_2, MAST_BIT_3, UP_ARROW, DOWN_ARROW, NONE);
            } else if (name.equals("changeDigital11")) {
                claw(CLAW);
            } else if (name.equals("changeDigital12")) {
                wrist(WRIST);
            } else if (name.equals("changeDigital13")) {
                elbow(ELBOW, true);
            } else if (name.equals("changeDigital10")) {
                minibotInterlock(MINIBOT);
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
            
            leftFrontMotor(LEFT_FRONT_STOP, LEFT_FRONT_BACKWARD, LEFT_FRONT_SPEED);
            rightFrontMotor(RIGHT_FRONT_STOP, RIGHT_FRONT_BACKWARD, RIGHT_FRONT_SPEED);
            leftBackMotor(LEFT_BACK_STOP, LEFT_BACK_BACKWARD, LEFT_BACK_SPEED);
            rightBackMotor(RIGHT_BACK_STOP, RIGHT_BACK_BACKWARD, RIGHT_BACK_SPEED);
            
            trigger("compressorStop");
            Claw.getInstance().wide();
            Wrist.getInstance().lower();
            Elbow.getInstance().raise();
            Minibot.getInstance().retract();
            
            trigger("mastStop");
            trigger("mastEncoderOverrideOff");
            trigger("mastFast");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital4") || name.equals("changeDigital5") || name.equals("changeDigital12")) {
                leftFrontMotor(LEFT_FRONT_STOP, LEFT_FRONT_BACKWARD, LEFT_FRONT_SPEED);
            } else if (name.equals("changeDigital6") || name.equals("changeDigital7") || name.equals("changeDigital13")) {
                rightFrontMotor(RIGHT_FRONT_STOP, RIGHT_FRONT_BACKWARD, RIGHT_FRONT_SPEED);
            } else if (name.equals("changeDigital8") || name.equals("changeDigital9") || name.equals("changeDigital14")) {
                leftBackMotor(LEFT_BACK_STOP, LEFT_BACK_BACKWARD, LEFT_BACK_SPEED);
            } else if (name.equals("changeDigital10") || name.equals("changeDigital11") || name.equals("changeDigital15")) {
                rightBackMotor(RIGHT_BACK_STOP, RIGHT_BACK_BACKWARD, RIGHT_BACK_SPEED);
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
            
            compressorMode(COMPRESSOR_AUTO);
            compressorState(COMPRESSOR_MANUAL_STATE);
            claw(CLAW);
            wrist(WRIST);
            elbow(ELBOW, false);
            minibot(MINIBOT);
            
            trigger("mastStop");
            trigger("mastEncoderOverrideOff");
            trigger("mastFast");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital7")) {
                compressorMode(COMPRESSOR_AUTO);
            } else if (name.equals("changeDigital8")) {
                compressorState(COMPRESSOR_MANUAL_STATE);
            } else if (name.equals("changeDigital4")) {
                claw(CLAW);
            } else if (name.equals("changeDigital5")) {
                wrist(WRIST);
            } else if (name.equals("changeDigital6")) {
                elbow(ELBOW, false);
            } else if (name.equals("changeDigital9")) {
                minibot(MINIBOT);
            }
        }
    }
    
    private class MastScreen extends TouchPanelScreen {
        private static final int MAST_BIT_1            = 4;
        private static final int MAST_BIT_2            = 5;
        private static final int MAST_BIT_3            = 6;
        private static final int POSITION              = 7;
        private static final int UP_ARROW              = 8;
        private static final int DOWN_ARROW            = 9;
        private static final int NONE                  = 14;
        private static final int MAST_ENCODER_OVERRIDE = 15;
        
        public void bound() {
            System.out.println("[mortouch] Mast screen bound.");
            
            trigger("stopMotors");
            
            trigger("compressorStop");
            Claw.getInstance().wide();
            Wrist.getInstance().lower();
            Elbow.getInstance().raise();
            Minibot.getInstance().retract();
            
            mast(POSITION, MAST_BIT_1, MAST_BIT_2, MAST_BIT_3, UP_ARROW, DOWN_ARROW, NONE);
            mastEncoderOverride(MAST_ENCODER_OVERRIDE);
            trigger("mastFast");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital4") || name.equals("changeDigital5") || name.equals("changeDigital6") || name.equals("changeDigital7") || name.equals("changeDigital8") || name.equals("changeDigital9") || name.equals("changeDigital14")) {
                mast(POSITION, MAST_BIT_1, MAST_BIT_2, MAST_BIT_3, UP_ARROW, DOWN_ARROW, NONE);
            } else if (name.equals("changeDigital15")) {
                mastEncoderOverride(MAST_ENCODER_OVERRIDE);
            }
        }
    }
    
    private boolean getDigital(int channel) {
        try {
            return !ds.getDigital(channel);
        } catch (DriverStationEnhancedIO.EnhancedIOException e) {
            return false;
        }
    }
    
    private void leftFrontMotor(int stopChannel, int backwardChannel, int speedChannel) {
        motor("LeftFront", stopChannel, backwardChannel, speedChannel);
    }
    
    private void rightFrontMotor(int stopChannel, int backwardChannel, int speedChannel) {
        motor("RightFront", stopChannel, backwardChannel, speedChannel);
    }
    
    private void leftBackMotor(int stopChannel, int backwardChannel, int speedChannel) {
        motor("LeftBack", stopChannel, backwardChannel, speedChannel);
    }
    
    private void rightBackMotor(int stopChannel, int backwardChannel, int speedChannel) {
        motor("RightBack", stopChannel, backwardChannel, speedChannel);
    }
    
    private void motor(String name, int stopChannel, int backwardChannel, int speedChannel) {
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
    
    private void compressorMode(int channel) {
        if (getDigital(channel)) {
            trigger("compressorAuto");
        } else {
            trigger("compressorManual");
        }
    }
    
    private void compressorState(int channel) {
        if (getDigital(channel)) {
            trigger("compressorManualOn");
        } else {
            trigger("compressorManualOff");
        }
    }
    
    private void claw(int channel) {
        if (getDigital(channel)) {
            Claw.getInstance().narrow();
        } else {
            Claw.getInstance().wide();
        }
    }
    
    private void wrist(int channel) {
        if (getDigital(channel)) {
            Wrist.getInstance().raise();
        } else {
            Wrist.getInstance().lower();
        }
    }
    
    private void elbow(int channel, boolean shouldSlowMast) {
        if (getDigital(channel)) {
            Elbow.getInstance().lower();
            
            if (shouldSlowMast) {
                trigger("mastSlow");
            }
        } else {
            Elbow.getInstance().raise();
            trigger("mastFast");
        }
    }
    
    private void minibotInterlock(int channel) {
        Minibot.getInstance().setInterlock(getDigital(channel));
    }
    
    private void minibot(int channel) {
        if (getDigital(channel)) {
            Minibot.getInstance().deploy();
        } else {
            Minibot.getInstance().retract();
        }
    }
    
    private void mast(int positionChannel, int bitChannel1, int bitChannel2, int bitChannel3, int upChannel, int downChannel, int noneChannel) {
        if (getDigital(positionChannel)) {
            if (getDigital(upChannel)) {
                trigger("mastRelativeUp");
            } else if (getDigital(downChannel)) {
                trigger("mastRelativeDown");
            } else {
                trigger("mastRelativeStop");
            }
        } else {
            if (getDigital(noneChannel)) {
                trigger("mastNone");
            } else {
                boolean bit1 = getDigital(bitChannel1);
                boolean bit2 = getDigital(bitChannel2);
                boolean bit3 = getDigital(bitChannel3);
                
                if (!bit1 && !bit2 && !bit3) {
                    trigger("mastGround");
                } else if (bit1 && !bit2 && !bit3) {
                    trigger("mastCenterCenter");
                } else if (!bit1 && bit2 && !bit3) {
                    trigger("mastCenterTop");
                } else if (bit1 && bit2 && !bit3) {
                    trigger("mastCenterBottom");
                } else if (!bit1 && !bit2 && bit3) {
                    trigger("mastSideTop");
                } else if (bit1 && !bit2 && bit3) {
                    trigger("mastSideBottom");
                } else if (!bit1 && bit2 && bit3) {
                    trigger("mastSideCenter");
                } else if (bit1 && bit2 && bit3) {
                    trigger("mastFeed");
                }
            }
        }
    }
    
    private void mastEncoderOverride(int channel) {
        if (getDigital(channel)) {
            trigger("mastEncoderOverrideOn");
        } else {
            trigger("mastEncoderOverrideOff");
        }
    }
}
