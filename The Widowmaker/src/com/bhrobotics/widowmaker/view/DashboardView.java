package com.bhrobotics.widowmaker.view;

import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import com.bhrobotics.widowmaker.model.Crio;

// Displays data on the dashboard.
public class DashboardView {
    
    private static Dashboard dashboard;

    public static void render(Crio crio) {
        dashboard = crio.getDashboardPackerLow();

        dashboard.addCluster(); {
            // Analog modules
            dashboard.addCluster(); {
                analogModuleCluster(crio.getAnalogBumper());
                analogModuleCluster(crio.getSolenoidBumper());
            } dashboard.finalizeCluster();

            // Digital modules
            dashboard.addCluster(); {
                digitalModuleCluster(crio.getMiscSidecar());
                digitalModuleCluster(crio.getMotorSidecar());
            } dashboard.finalizeCluster();

            // Solenoids
            dashboard.addByte(crio.getSolenoids());
        } dashboard.finalizeCluster();

        // Sends the data to the dashboard.
        dashboard.commit();
    }

    // Run once for each of the analog modules. Uses the average voltage of each
    // analog input for its value. Somehow, this works.
    private static void analogModuleCluster(AnalogModule module) {
        dashboard.addCluster(); {
            for(int i = 1; i <= 8; i++) {
                dashboard.addFloat((float) module.getAverageVoltage(i));
            }
        } dashboard.finalizeCluster();
    }

    // Run once for each digital module. For some reason, digital modules need
    // to have two nested clusters. I really have no clue why.
    private static void digitalModuleCluster(DigitalModule module) {
        dashboard.addCluster(); {
            dashboard.addCluster(); {

                // Relays
                dashboard.addByte(module.getRelayForward());
                dashboard.addByte(module.getRelayReverse());

                // Digital IO
                dashboard.addShort(module.getAllDIO());
                dashboard.addShort(module.getDIODirection());

                // All PWM digital inputs.
                dashboard.addCluster(); {
                    for(int i = 1; i <= 10; i++) {
                        dashboard.addByte((byte) module.getPWM(i));
                    }
                } dashboard.finalizeCluster();
            } dashboard.finalizeCluster();
        } dashboard.finalizeCluster();
    }
}