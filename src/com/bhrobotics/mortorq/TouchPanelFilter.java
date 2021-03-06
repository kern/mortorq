package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Filter;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Reactor;
import edu.wpi.first.wpilibj.DriverStation;

public class TouchPanelFilter extends Filter {
    private static final int NUM_SCREENS = 5;
    protected EventEmitter emitter         = new EventEmitter();
    private TouchPanelScreen[] screens   = new TouchPanelScreen[NUM_SCREENS];
    private TouchPanelScreen currentScreen;
    
    public void handle(Event event) {
        if(event.getName() == "changeDigitals") {
            int digitals = ((Short) event.getData().get("newValue")).shortValue();
            
            if ((digitals & 0x0001) == 1) {
                int msb = ((digitals & 0x0002) >> 1) == 1 ? 0 : 2;
                int lsb = ((digitals & 0x0004) >> 2) == 1 ? 0 : 1;
                
                setCurrentScreen(msb + lsb + 1);
            } else {
                setCurrentScreen(0);
            }
        } else {
            if (currentScreen != null) {
                currentScreen.handle(event);
            }
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public TouchPanelScreen getScreen(int tag) {
        return screens[tag];
    }
    
    public void setScreen(int tag, TouchPanelScreen screen) {
        screen.setPanel(this);
        screens[tag] = screen;
    }
    
    public TouchPanelScreen getCurrentScreen() {
        return currentScreen;
    }
    
    public void setCurrentScreen(int tag) {
        TouchPanelScreen newScreen = screens[tag];
        
        if (currentScreen != newScreen) {
            if (currentScreen != null) {
                currentScreen.setBound(false);
            }
            
            currentScreen = newScreen;
            
            if (currentScreen != null) {
                currentScreen.setBound(true);
            }
        }
    }
}