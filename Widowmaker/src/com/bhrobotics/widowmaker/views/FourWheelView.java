package com.bhrobotics.widowmaker.views;

import com.bhrobotics.morlib.View;
import com.bhrobotics.widowmaker.models.DriveTrain;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;

// Uses four wheels to control a robot.
public class FourWheelView implements View {

    private DriveTrain driveTrain;

    //**************************************************************************
    // Motors
    //**************************************************************************

    private static final int SLOT = 6;
    private static final int RIGHT_FRONT = 4;
    private static final int RIGHT_BACK = 2;
    private static final int LEFT_FRONT = 5;
    private static final int LEFT_BACK = 3;

    private Jaguar rightFront = new Jaguar(SLOT, RIGHT_FRONT);
    private Jaguar rightBack = new Jaguar(SLOT, RIGHT_BACK);
    private Jaguar leftFront = new Jaguar(SLOT, LEFT_FRONT);
    private Jaguar leftBack = new Jaguar(SLOT, LEFT_BACK);

    //**************************************************************************
    // Polarity
    //**************************************************************************

    private static final int RIGHT_FRONT_POLARITY = 1;
    private static final int RIGHT_BACK_POLARITY = 1;
    private static final int LEFT_FRONT_POLARITY = -1;
    private static final int LEFT_BACK_POLARITY = -1;

    //**************************************************************************
    // Encoders
    //**************************************************************************

    private static final int ENCODER_SLOT = 4;

    private static final int RIGHT_FRONT_FORWARD = 11;
    private static final int RIGHT_FRONT_REVERSE = 10;

    private Encoder rightFrontEncoder = new Encoder(ENCODER_SLOT, RIGHT_FRONT_FORWARD,
                                                    ENCODER_SLOT, RIGHT_FRONT_REVERSE);

    //**************************************************************************
    // Relays
    //**************************************************************************

    private static final int RELAY_SLOT = 4;
    private static final int RIGHT_FRONT_RELAY = 3;

    private Relay rightFrontRelay = new Relay(RELAY_SLOT, RIGHT_FRONT_RELAY);

    //**************************************************************************
    // Interface
    //**************************************************************************
    
    public FourWheelView(DriveTrain _driveTrain) {
        driveTrain = _driveTrain;
    }

    public void update() {
        driveTrain.setRightFrontRate(rightFrontEncoder.getRate());
        //driveTrain.setRightBackRate(rightBackEncoder.getRate());
        //driveTrain.setLeftFrontRate(leftFrontEncoder.getRate());
        //driveTrain.setLeftBackRate(leftBackEncoder.getRate());
    }

    public void render() {
        rightFront.set(driveTrain.getRightFront() * RIGHT_FRONT_POLARITY);
        rightBack.set(driveTrain.getRightBack() * RIGHT_BACK_POLARITY);
        leftFront.set(driveTrain.getLeftFront() * LEFT_FRONT_POLARITY);
        leftBack.set(driveTrain.getLeftBack() * LEFT_BACK_POLARITY);
        
        if(rightFrontEncoder.getDirection()) {
            rightFrontRelay.set(Relay.Value.kForward);
        }else{
            rightFrontRelay.set(Relay.Value.kReverse);
        }
    }
}