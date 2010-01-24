package com.bhrobotics.widowmaker;

import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;

public class DashboardInterface {

    Dashboard _dashLow;

    /**
     * Stolen directly out of DashBoardExample with a little bit of refactoring.
     */
    void update() {

        /* Need to get a new dashboard packer for each time this function is
         * run. The _dashLow variable is used to keep state so that it doesn't
         * need to be passed around to the helper functions.
         */
        _dashLow = DriverStation.getInstance().getDashboardPackerLow();

        _dashLow.addCluster(); {
            // Analog modules
            _dashLow.addCluster(); {
                add_analog_module(1);
                add_analog_module(2);
            }
            _dashLow.finalizeCluster();

            // Digital modules
            _dashLow.addCluster(); {
                add_digital_module(4);
                add_digital_module(6);
            }
            _dashLow.finalizeCluster();

            add_solenoids();
        }
        _dashLow.finalizeCluster();

        // Sends the data to the dashboard.
        _dashLow.commit();
    }

    /**
     * Run once for each of the analog modules.
     */
    void add_analog_module(int moduleNum) {
        _dashLow.addCluster(); {
            for(int i = 1; i <= 8; i++) {
                AnalogModule module = AnalogModule.getInstance(moduleNum);
                _dashLow.addFloat((float) module.getAverageVoltage(i));
            }
        }
        _dashLow.finalizeCluster();
    }

    /**
     * Run once for each digital module. For some reason, digital modules need
     * to have two nested clusters. I really have no clue why.
     */
    void add_digital_module(int moduleNum) {
        DigitalModule module = DigitalModule.getInstance(moduleNum);

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

    /**
     * Merely used for abstraction purposes.
     */
    void add_solenoids() {
        _dashLow.addByte(Solenoid.getAll());
    }
}