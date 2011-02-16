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
        public void bound() {
            System.out.println("[mortouch] Drive train screen bound.");
            trigger("stopMotors");
            // trigger("mastStop");
            // trigger("compressorStop");
        }
        
        public void handle(Event event) {
            String name = event.getName();
            
            if (name.equals("changeDigital4") || name.equals("changeDigital5") || name.equals("changeDigital12")) {
                boolean digital4  = getDigital(4);
                boolean digital5  = getDigital(5);
                boolean digital12 = getDigital(12);
                
                double value;
                
                if (!digital4 && digital5) {
                    value = 0.0;
                } else if (digital4 && digital5) {
                    value = -1.0;
                } else {
                    value = 1.0;
                }
                
                if (digital12) {
                    value /= 2.0;
                }
                
                Hashtable data = new Hashtable();
                data.put("value", new Double(value));
                
                trigger("changeMotorLeftFront", data);
            }
            
            if (name.equals("changeDigital6") || name.equals("changeDigital7") || name.equals("changeDigital13")) {
                boolean digital6  = getDigital(6);
                boolean digital7  = getDigital(7);
                boolean digital13 = getDigital(13);
                
                double value;
                
                if (!digital6 && digital7) {
                    value = 0.0;
                } else if (digital6 && digital7) {
                    value = -1.0;
                } else {
                    value = 1.0;
                }
                
                if (digital13) {
                    value /= 2.0;
                }
                
                Hashtable data = new Hashtable();
                data.put("value", new Double(value));
                
                trigger("changeMotorRightFront", data);
            }
            
            if (name.equals("changeDigital8") || name.equals("changeDigital9") || name.equals("changeDigital14")) {
                boolean digital8  = getDigital(8);
                boolean digital9  = getDigital(9);
                boolean digital14 = getDigital(14);
                
                double value;
                
                if (!digital8 && digital9) {
                    value = 0.0;
                } else if (digital8 && digital9) {
                    value = -1.0;
                } else {
                    value = 1.0;
                }
                
                if (digital14) {
                    value /= 2.0;
                }
                
                Hashtable data = new Hashtable();
                data.put("value", new Double(value));
                
                trigger("changeMotorLeftBack", data);
            }
            
            if (name.equals("changeDigital10") || name.equals("changeDigital11") || name.equals("changeDigital15")) {
                boolean digital10 = getDigital(10);
                boolean digital11 = getDigital(11);
                boolean digital15 = getDigital(15);
                
                double value;
                
                if (!digital10 && digital11) {
                    value = 0.0;
                } else if (digital10 && digital11) {
                    value = -1.0;
                } else {
                    value = 1.0;
                }
                
                if (digital15) {
                    value /= 2.0;
                }
                
                Hashtable data = new Hashtable();
                data.put("value", new Double(value));
                
                trigger("changeMotorRightBack", data);
            }
        }
    }
    
    private boolean getDigital(int channel) {
        try {
            return ds.getDigital(channel);
        } catch (DriverStationEnhancedIO.EnhancedIOException e) {
            return false;
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
