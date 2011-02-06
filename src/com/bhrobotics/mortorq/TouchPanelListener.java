package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;

public class TouchpanelListener implements Listener {
    private static final int NUM_SCREENS = 8;
    private EventEmitter emitter = new EventEmitter();
    private Screen[] screens = new Screen[NUM_SCREENS];
    private Screen currentScreen;
    
    public void handle(Event event) {
        Short digitals = (Short) event.getData().get("newDigitals");
        int digits = digitals.shortValue();
        int newScreenTag = (digits & 0xE000) >> 13;
        Screen newScreen = screens[newScreenTag];
        
        if (newScreen != currentScreen) {
            currentScreen = newScreen;
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
    
    public interface Screen {
        public void handle(EventEmitter p, Event event);
    }
}