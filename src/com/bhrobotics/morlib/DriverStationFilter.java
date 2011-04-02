package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import java.util.Hashtable;

public class DriverStationFilter extends EventEmitter implements Listener {
    private DriverStationEnhancedIO ds = DriverStation.getInstance().getEnhancedIO();
    
    private static final int ANALOGS  = 8;
    private static final int DIGITALS = 16;
    
    private DSAnalogInput[] analogs   = new DSAnalogInput[ANALOGS];
    private DSDigitalInput[] digitals = new DSDigitalInput[DIGITALS];
    
    private short oldDigitals;
    private byte oldButtons;
    
    public DriverStationFilter() {
        for (int i = 0; i < ANALOGS; i++) {
            analogs[i] = new DSAnalogInput(i + 1);
        }
        
        for (int j = 0; j < DIGITALS; j++) {
            digitals[j] = new DSDigitalInput(j + 1);
        }
    }
    
    public void handle(Event event) {
        update(false);
    }
    
    public void bound(EventEmitter emitter, String event) {
        update(false);
    }
    
    public void update(boolean forceEmit) {
        updateAllAnalogs(forceEmit);
        updateDigitalsShort(forceEmit);
        updateAllDigitals(forceEmit);
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
}