package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;

public class DashboardInterface {

    Dashboard _dashLow;
    AnalogModule _analogModule1;
    AnalogModule _analogModule2;
    DigitalModule _motorModule;
    DigitalModule _digitalModule2;

    DashboardInterface(AnalogModule analogModule1, AnalogModule analogModule2,
                       DigitalModule motorModule, DigitalModule digitalModule2) {
        // Make sure the modules aren't invalid
        if(analogModule1 == null) {
            throw new IllegalArgumentException("Analog module 1 is null!");
        }

        if(analogModule2 == null) {
            throw new IllegalArgumentException("Analog module 2 is null!");
        }

        if(motorModule == null) {
            throw new IllegalArgumentException("Motor module is null!");
        }
        
        if(digitalModule2 == null) {
            throw new IllegalArgumentException("Digital module 2 is null!");
        }

        _analogModule1 = analogModule1;
        _analogModule2 = analogModule2;
        _motorModule = motorModule;
        _digitalModule2 = digitalModule2;
    }

    /**
     * Stolen directly out of DashBoardExample with a little bit of refactoring.
     **/
    void updateLow() {
        // Need to get a new dashboard packer for each time this function is
        // run. The _dashLow variable is used to keep state so that it doesn't
        // need to be passed around to the helper functions.
        _dashLow = DriverStation.getInstance().getDashboardPackerLow();

        _dashLow.addCluster(); {
            // Analog modules
            _dashLow.addCluster(); {
                analog_module_cluster(_analogModule1);
                analog_module_cluster(_analogModule2);
            }
            _dashLow.finalizeCluster();

            // Digital modules
            _dashLow.addCluster(); {
                digital_module_cluster(_motorModule);
                digital_module_cluster(_digitalModule2);
            }
            _dashLow.finalizeCluster();

            // Solenoids
            _dashLow.addByte(Solenoid.getAll());
        }
        _dashLow.finalizeCluster();

        // Sends the data to the dashboard.
        _dashLow.commit();
    }

    /**
     * Run once for each of the analog modules. Uses the average voltage of each
     * analog input for its value.
     **/
    void analog_module_cluster(AnalogModule module) {
        _dashLow.addCluster(); {
            for(int i = 1; i <= 8; i++) {
                _dashLow.addFloat((float) module.getAverageVoltage(i));
            }
        }
        _dashLow.finalizeCluster();
    }

    /**
     * Run once for each digital module. For some reason, digital modules need
     * to have two nested clusters. I really have no clue why.
     **/
    void digital_module_cluster(DigitalModule module) {
        _dashLow.addCluster(); {
            _dashLow.addCluster(); {

                /* No idea why these lines are duplicated; however, this was in
                 * the default code so I'm keeping it.
                 */
                _dashLow.addByte(module.getRelayForward());
                _dashLow.addByte(module.getRelayForward());

                _dashLow.addShort(module.getAllDIO());
                _dashLow.addShort(module.getDIODirection());

                // All PWM digital inputs.
                _dashLow.addCluster(); {
                    for(int i = 1; i <= 10; i++) {
                        _dashLow.addByte((byte) module.getPWM(i));
                    }
                }
                _dashLow.finalizeCluster();
            }
            _dashLow.finalizeCluster();
        }
        _dashLow.finalizeCluster();
    }
}