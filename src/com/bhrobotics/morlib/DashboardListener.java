package com.bhrobotics.morlib;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Dashboard;
import edu.wpi.first.wpilibj.AnalogModule;
import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.Solenoid;

public class DashboardListener implements Listener {
    public void handle(Event event) {
        Dashboard lowDashData = DriverStation.getInstance().getDashboardPackerLow();
        
        lowDashData.addCluster(); {
            lowDashData.addCluster(); {
                // Analog modules
                lowDashData.addCluster(); {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(1).getAverageVoltage(i));
                    }
                } lowDashData.finalizeCluster();
                
                lowDashData.addCluster(); {
                    for (int i = 1; i <= 8; i++) {
                        lowDashData.addFloat((float) AnalogModule.getInstance(2).getAverageVoltage(i));
                    }
                } lowDashData.finalizeCluster();
            } lowDashData.finalizeCluster();
            
            lowDashData.addCluster(); {
                // Digital modules
                lowDashData.addCluster(); {
                    lowDashData.addCluster(); {
                        int module = 4;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster(); {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        } lowDashData.finalizeCluster();
                    } lowDashData.finalizeCluster();
                } lowDashData.finalizeCluster();
                
                lowDashData.addCluster(); {
                    lowDashData.addCluster(); {
                        int module = 6;
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayForward());
                        lowDashData.addByte(DigitalModule.getInstance(module).getRelayReverse());
                        lowDashData.addShort(DigitalModule.getInstance(module).getAllDIO());
                        lowDashData.addShort(DigitalModule.getInstance(module).getDIODirection());
                        lowDashData.addCluster(); {
                            for (int i = 1; i <= 10; i++) {
                                lowDashData.addByte((byte) DigitalModule.getInstance(module).getPWM(i));
                            }
                        } lowDashData.finalizeCluster();
                    } lowDashData.finalizeCluster();
                } lowDashData.finalizeCluster();
            } lowDashData.finalizeCluster();
            
            lowDashData.addByte(Solenoid.getAllFromDefaultModule());
            
        } lowDashData.finalizeCluster();
        lowDashData.commit();
    }
    
    public void bound(EventEmitter emitter, String name) {}
    public void unbound(EventEmitter emitter, String name) {}
}