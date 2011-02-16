package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Relay;

public class CompressorListener implements Listener {
    private static final int PRESSURE_SLOT    = 4;
    private static final int PRESSURE_CHANNEL = 14;
    
    private static final int RELAY_SLOT    = 6;
    private static final int RELAY_CHANNEL = 1;
    
    private Compressor compressor = new Compressor(PRESSURE_SLOT, PRESSURE_CHANNEL, RELAY_SLOT, RELAY_CHANNEL);
    
    private boolean auto;
    private boolean manualState;
    
    public CompressorListener() {
        stop();
    }
    
    public void handle(Event event) {
        String name = event.getName();
        
        if (name.equals("compressorAuto")) {
            auto();
        } else if (name.equals("compressorManual")) {
            manual();
        } else if (name.equals("compressorManualOn")) {
            manualState = true;
            
            if (!auto) {
                updateManual();
            }
        } else if (name.equals("compressorManualOff")) {
            manualState = false;
            
            if (!auto) {
                updateManual();
            }
        } else if (name.equals("compressorStop")) {
            stop();
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public void auto() {
        auto = true;
        compressor.start();
    }
    
    public void manual() {
        auto = false;
        compressor.stop();
        updateManual();
    }
    
    public void stop() {
        manualState = false;
        manual();
    }
    
    public boolean getManualState() {
        return manualState;
    }
    
    public void setManualState(boolean m) {
        manualState = m;
    }
    
    private void updateManual() {
        if (manualState) {
            compressor.setRelayValue(Relay.Value.kOn);
        } else {
            compressor.setRelayValue(Relay.Value.kOff);
        }
    }
}
