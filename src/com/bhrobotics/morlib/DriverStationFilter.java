package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import java.util.Hashtable;

public class DriverStationFilter extends Filter {
    private EventEmitter emitter = new EventEmitter();
    private DriverStationEnhancedIO ds = DriverStation.getInstance().getEnhancedIO();
    
    private static final int ANALOGS  = 8;
    private static final int DIGITALS = 16;
    private static final int ACCELS   = 3;
    private static final int BUTTONS  = 6;
    
    private DSAnalogInput[] analogs   = new DSAnalogInput[ANALOGS];
    private DSDigitalInput[] digitals = new DSDigitalInput[DIGITALS];
    private DSAccelInput[] accels     = new DSAccelInput[ACCELS];
    private DSButtonInput[] buttons   = new DSButtonInput[BUTTONS];
    
    private short oldDigitals;
    private byte oldButtons;
    
    public DriverStationFilter() {
        for (int i = 0; i < ANALOGS; i++) {
            analogs[i] = new DSAnalogInput(i + 1);
        }
        
        for (int j = 0; j < DIGITALS; j++) {
            digitals[j] = new DSDigitalInput(j + 1);
        }
        
        accels[0] = new DSAccelInput(DriverStationEnhancedIO.tAccelChannel.kAccelX);
        accels[1] = new DSAccelInput(DriverStationEnhancedIO.tAccelChannel.kAccelY);
        accels[2] = new DSAccelInput(DriverStationEnhancedIO.tAccelChannel.kAccelZ);
        
        for (int j = 0; j < BUTTONS; j++) {
            buttons[j] = new DSButtonInput(j + 1);
        }
    }
    
    public void handle(Event event) {
        update(false);
    }
    
    public void bound(EventEmitter emitter, String event) {
        update(false);
    }
    
    public void unbound(EventEmitter emitter, String event) {}
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void update(boolean forceEmit) {
        updateAllAnalogs(forceEmit);
        updateDigitalsShort(forceEmit);
        updateAllDigitals(forceEmit);
        updateAllAccels(forceEmit);
        updateButtonsByte(forceEmit);
        updateAllButtons(forceEmit);
    }
    
    public void updateAllAnalogs(boolean forceEmit) {
        for (int j = 0; j < ANALOGS; j++) {
            analogs[j].update(forceEmit);
        }
    }
    
    public void updateDigitalsShort(boolean forceEmit) {
        try {
            short newDigitals = ds.getDigitals();
            
            if (oldDigitals != newDigitals || forceEmit) {
                Hashtable data = new Hashtable();
                data.put("oldValue", new Short(oldDigitals));
                data.put("newValue", new Short(newDigitals));
                
                trigger("changeDigitals", data);
            }
            
            oldDigitals = newDigitals;
        } catch (DriverStationEnhancedIO.EnhancedIOException e) {
            // Ignore.
        }
    }
    
    public void updateAllDigitals(boolean forceEmit) {
        for (int j = 0; j < DIGITALS; j++) {
            digitals[j].update(forceEmit);
        }
    }
    
    public void updateAllAccels(boolean forceEmit) {
        for (int j = 0; j < ACCELS; j++) {
            accels[j].update(forceEmit);
        }
    }
    
    public void updateButtonsByte(boolean forceEmit) {
        try {
            byte newButtons = ds.getButtons();
            
            if (oldButtons != newButtons || forceEmit) {
                Hashtable data = new Hashtable();
                data.put("oldValue", new Byte(oldButtons));
                data.put("newValue", new Byte(newButtons));
                
                trigger("changeButtons", data);
            }
            
            oldButtons = newButtons;
        } catch (DriverStationEnhancedIO.EnhancedIOException e) {
            // Ignore.
        }
    }
    
    public void updateAllButtons(boolean forceEmit) {
        for (int j = 0; j < BUTTONS; j++) {
            buttons[j].update(forceEmit);
        }
    }
    
    private class DSAnalogInput {
        private int channel;
        private double oldValue;
        
        public DSAnalogInput(int c) {
            channel = c;
        }
        
        public void update(boolean forceEmit) {
            try {
                double newValue = ds.getAnalogIn(channel);
                
                if (oldValue != newValue || forceEmit) {
                    Hashtable data = new Hashtable();
                    data.put("oldValue", new Double(oldValue));
                    data.put("newValue", new Double(newValue));
                    
                    trigger("changeAnalog" + channel, data);
                }
                
                oldValue = newValue;
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
    }
    
    private class DSDigitalInput {
        private int channel;
        private boolean oldValue;
        
        public DSDigitalInput(int c) {
            channel = c;
        }
        
        public void update(boolean forceEmit) {
            try {
                boolean newValue = ds.getDigital(channel);
                
                if (oldValue != newValue || forceEmit) {
                    Hashtable data = new Hashtable();
                    data.put("oldValue", new Boolean(oldValue));
                    data.put("newValue", new Boolean(newValue));
                    
                    trigger("changeDigital" + channel, data);
                }
                
                oldValue = newValue;
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
    }
    
    private class DSAccelInput {
        private DriverStationEnhancedIO.tAccelChannel channel;
        private double oldValue;
        
        public DSAccelInput(DriverStationEnhancedIO.tAccelChannel c) {
            channel = c;
        }
        
        public void update(boolean forceEmit) {
            try {
                double newValue = ds.getAcceleration(channel);
                
                if (oldValue != newValue || forceEmit) {
                    Hashtable data = new Hashtable();
                    data.put("oldValue", new Double(oldValue));
                    data.put("newValue", new Double(newValue));
                    
                    trigger("changeAccel" + channel, data);
                }
                
                oldValue = newValue;
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
    }
    
    private class DSButtonInput {
        private int channel;
        private boolean oldValue;
        
        public DSButtonInput(int c) {
            channel = c;
        }
        
        public void update(boolean forceEmit) {
            try {
                boolean newValue = ds.getButton(channel);
                
                if (oldValue != newValue || forceEmit) {
                    Hashtable data = new Hashtable();
                    data.put("oldValue", new Boolean(oldValue));
                    data.put("newValue", new Boolean(newValue));
                    
                    trigger("changeButton" + channel, data);
                }
                
                oldValue = newValue;
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
    }
}