package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.Compressor;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalInput;

public class CompressorView implements View {

    private Compressor compressor;

    //**************************************************************************
    // IO
    //**************************************************************************

    private static final int COMPRESSOR_SLOT = 6;
    private static final int COMPRESSOR = 1;

    private static final int SENSOR_SLOT = 4;
    private static final int SENSOR = 12;

    private Relay relay = new Relay(COMPRESSOR_SLOT, COMPRESSOR);
    private DigitalInput sensor = new DigitalInput(SENSOR_SLOT, SENSOR);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public CompressorView(Compressor _compressor) {
        compressor = _compressor;
    }

    // Everything's handled outside this library, so no need for polling.
    public void update() { compressor.setSensor(sensor.get()); }
    public void render() {
        if(compressor.get()) {
            relay.set(Relay.Value.kOn);
        }else{
            relay.set(Relay.Value.kOff);
        }
    }
}