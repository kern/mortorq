package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Filter;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Reactor;

public class TouchPanelFilter extends Filter {
    private static final int NUM_SCREENS = 5;
    private EventEmitter emitter         = new EventEmitter();
    private TouchPanelScreen[] screens   = new TouchPanelScreen[NUM_SCREENS];
    private TouchPanelScreen currentScreen;
    
    public void handle(Event event) {
        if(event.getName() == "updateDigitals") {
            int digitals = ((Short) event.getData().get("newDigitals")).shortValue();
            
            if ((digitals & 0x0001) == 0) {
                setCurrentScreen(((digitals & 0x0006) >> 14) + 1);
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