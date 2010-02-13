package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import edu.wpi.first.wpilibj.Compressor;

public class CompressorView implements View {

    //**************************************************************************
    // IO
    //**************************************************************************

    private static final int COMPRESSOR_SLOT = 6;
    private static final int COMPRESSOR = 1;

    private static final int SENSOR_SLOT = 4;
    private static final int SENSOR = 12;

    private Compressor compressor = new Compressor(COMPRESSOR_SLOT, COMPRESSOR,
                                                   SENSOR_SLOT, SENSOR);

    //**************************************************************************
    // Interface
    //**************************************************************************

    public CompressorView() {
        compressor.start();
    }

    // Everything's handled outside this library, so no need for polling.
    public void update() {}
    public void render() {}
}