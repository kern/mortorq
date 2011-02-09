package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import java.util.Hashtable;

// TODO: Test me.
public class DSInputFilter extends Filter {
    protected DriverStation ds = DriverStation.getInstance();
    protected DriverStationEnhancedIO dsEIO = ds.getEnhancedIO();
    private EventEmitter emitter = new EventEmitter();
    
    private short oldDigitals;
    
    private DSAnalogInput analog1 = new DSAnalogInput(1);
    private DSAnalogInput analog2 = new DSAnalogInput(2);
    private DSAnalogInput analog3 = new DSAnalogInput(3);
    private DSAnalogInput analog4 = new DSAnalogInput(4);
    private DSAnalogInput analog5 = new DSAnalogInput(5);
    private DSAnalogInput analog6 = new DSAnalogInput(6);
    private DSAnalogInput analog7 = new DSAnalogInput(7);
    private DSAnalogInput analog8 = new DSAnalogInput(8);
    
    private DSDigitalInput digital1  = new DSDigitalInput(1);
    private DSDigitalInput digital2  = new DSDigitalInput(2);
    private DSDigitalInput digital3  = new DSDigitalInput(3);
    private DSDigitalInput digital4  = new DSDigitalInput(4);
    private DSDigitalInput digital5  = new DSDigitalInput(5);
    private DSDigitalInput digital6  = new DSDigitalInput(6);
    private DSDigitalInput digital7  = new DSDigitalInput(7);
    private DSDigitalInput digital8  = new DSDigitalInput(8);
    private DSDigitalInput digital9  = new DSDigitalInput(9);
    private DSDigitalInput digital10 = new DSDigitalInput(10);
    private DSDigitalInput digital11 = new DSDigitalInput(11);
    private DSDigitalInput digital12 = new DSDigitalInput(12);
    private DSDigitalInput digital13 = new DSDigitalInput(13);
    private DSDigitalInput digital14 = new DSDigitalInput(14);
    private DSDigitalInput digital15 = new DSDigitalInput(15);
    private DSDigitalInput digital16 = new DSDigitalInput(16);
    
    public void handle(Event event) {
        updateAll(true);
    }
    
    public void bound(EventEmitter emitter, String event) {
        updateAll(false);
    }
    
    public void unbound(EventEmitter emitter, String event) {}
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    private void updateAll(boolean shouldEmit) {
        analog1.update(shouldEmit);
        analog2.update(shouldEmit);
        analog3.update(shouldEmit);
        analog4.update(shouldEmit);
        analog5.update(shouldEmit);
        analog6.update(shouldEmit);
        analog7.update(shouldEmit);
        analog8.update(shouldEmit);
        
        try {
            short newDigitals = dsEIO.getDigitals();
            
            if (newDigitals != oldDigitals) {
                Hashtable data = new Hashtable();
                data.put("oldDigitals", new Short(oldDigitals));
                data.put("newDigitals", new Short(newDigitals));
                
                emitter.trigger("updateDigitals", data);
            }
            
            oldDigitals = newDigitals;
        } catch (DriverStationEnhancedIO.EnhancedIOException e) {
            // Ignore.
        }
        
        digital1.update(shouldEmit);
        digital2.update(shouldEmit);
        digital3.update(shouldEmit);
        digital4.update(shouldEmit);
        digital5.update(shouldEmit);
        digital6.update(shouldEmit);
        digital7.update(shouldEmit);
        digital8.update(shouldEmit);
        digital9.update(shouldEmit);
        digital10.update(shouldEmit);
        digital11.update(shouldEmit);
        digital12.update(shouldEmit);
        digital13.update(shouldEmit);
        digital14.update(shouldEmit);
        digital15.update(shouldEmit);
        digital16.update(shouldEmit);
    }
    
    public void triggerAnalogUpdate(int channel, double oldValue, double newValue) {
        Hashtable data = new Hashtable();
        data.put("oldValue", new Double(oldValue));
        data.put("newValue", new Double(newValue));
        
        trigger("updateAnalog" + channel, data);
    }
    
    public void triggerDigitalUpdate(int channel, boolean oldValue, boolean newValue) {
        Hashtable data = new Hashtable();
        data.put("oldValue", new Boolean(oldValue));
        data.put("newValue", new Boolean(newValue));
        
        trigger("updateDigital" + channel, data);
    }
    
    private abstract class DSInput {
        protected int channel;
        
        public DSInput(int c) {
            channel = c;
        }
        
        public abstract void update(boolean shouldEmit);
    }
    
    private class DSAnalogInput extends DSInput {
        private double oldValue;
        
        public DSAnalogInput(int c) {
            super(c);
        }
        
        public void update(boolean shouldEmit) {
            try {
                double newValue = dsEIO.getAnalogIn(channel);
                
                if (oldValue != newValue && shouldEmit) {
                    triggerAnalogUpdate(channel, oldValue, newValue);
                }
                
                oldValue = newValue;
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
    }
    
    private class DSDigitalInput extends DSInput {
        private boolean oldValue;
        
        public DSDigitalInput(int c) {
            super(c);
        }
        
        public void update(boolean shouldEmit) {
            try {
                boolean newValue = dsEIO.getDigital(channel);
                
                if (oldValue != newValue && shouldEmit) {
                    triggerDigitalUpdate(channel, oldValue, newValue);
                }
                
                oldValue = newValue;
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
    }
}