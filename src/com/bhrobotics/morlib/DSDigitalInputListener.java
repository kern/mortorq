package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import java.util.Hashtable;

public class DSDigitalInputListener extends Listener {
    private DSDigitalInput input1  = new DSDigitalInput(1);
    private DSDigitalInput input2  = new DSDigitalInput(2);
    private DSDigitalInput input3  = new DSDigitalInput(3);
    private DSDigitalInput input4  = new DSDigitalInput(4);
    private DSDigitalInput input5  = new DSDigitalInput(5);
    private DSDigitalInput input6  = new DSDigitalInput(6);
    private DSDigitalInput input7  = new DSDigitalInput(7);
    private DSDigitalInput input8  = new DSDigitalInput(8);
    private DSDigitalInput input9  = new DSDigitalInput(9);
    private DSDigitalInput input10 = new DSDigitalInput(10);
    private DSDigitalInput input11 = new DSDigitalInput(11);
    private DSDigitalInput input12 = new DSDigitalInput(12);
    private DSDigitalInput input13 = new DSDigitalInput(13);
    private DSDigitalInput input14 = new DSDigitalInput(14);
    private DSDigitalInput input15 = new DSDigitalInput(15);
    private DSDigitalInput input16 = new DSDigitalInput(16);
    
    private EventEmitter emitter = new EventEmitter();
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public void handle(Event event) {
        input1.update();
        input2.update();
        input3.update();
        input4.update();
        input5.update();
        input6.update();
        input7.update();
        input8.update();
        input9.update();
        input10.update();
        input11.update();
        input12.update();
        input13.update();
        input14.update();
        input15.update();
        input16.update();
    }
    
    public void emitUpdate(int channel, boolean value) {
        Hashtable data = new Hashtable();
        data.put("value", new Boolean(value));
        
        emitter.emit("updateDSDigitalInput" + channel, data);
    }
    
    private class DSDigitalInput {
        private DriverStation ds = DriverStation.getInstance();
        private DriverStationEnhancedIO dsEIO = ds.getEnhancedIO();
        
        private int channel;
        private boolean value;
        
        public DSDigitalInput(int c) {
            channel = c;
            
            try {
                value = dsEIO.getDigital(channel);
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
        
        public void update() {
            boolean newValue;
            
            try {
                newValue = dsEIO.getDigital(channel);
                
                if (value != newValue) {
                    DSDigitalInputListener.this.emitUpdate(channel, newValue);
                    value = newValue;
                }
            } catch (DriverStationEnhancedIO.EnhancedIOException e) {
                // Ignore.
            }
        }
    }
}
