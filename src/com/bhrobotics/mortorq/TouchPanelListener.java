package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Reactor;

public class TouchPanelListener implements Listener {
    private static final int NUM_SCREENS = 5;
    private EventEmitter emitter         = new EventEmitter();
    private Screen[] screens             = new Screen[NUM_SCREENS];
    private Screen currentScreen;
    
    public void handle(Event event) {
        if(event.getName() == "updateDigitals") {
            int digitals = ((Short) event.getData().get("newDigitals")).shortValue();
            
            if ((digitals & 0x0001) == 0) {
                int newScreenTag = ((digitals & 0xC000) >> 14) + 1;
                currentScreen = screens[newScreenTag];
            } else {
                currentScreen = screens[0];
            }
        } else {
            currentScreen.handle(emitter, event);
        }
    }
    
    public void bound(String event, EventEmitter emitter) {}
    public void unbound(String event, EventEmitter emitter) {}
    
    public void addScreen(int tag, Screen screen) {
        screens[tag] = screen;
    }
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public Screen getCurrentScreen() {
        return currentScreen;
    }
    
    public Screen[] getScreens() {
        return screens; 
    }
    
    public interface Screen {
        public void handle(EventEmitter emitter, Event event);
    }
}