package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;

public class TouchPanelListener extends Listener {
    private static final int NUM_SCREENS = 8;
    private EventEmitter emitter = new EventEmitter();
    private Screen[] screens = new Screen[NUM_SCREENS];
    private int currentScreen = -1;
    
    public void handle(Event event) {
        Short digitals = (Short) event.getData().get("newDigitals");
        int digits = digitals.shortValue();
        int newScreen = (digits & 0xE000) >> 13;
        if (newScreen != currentScreen) {
            currentScreen = newScreen;
        } else {
            screens[currentScreen].handle(this, event);
        }
    }
    
    public void addScreen(int tag, Screen screen) {
        screens[tag] = screen;
    }
    
    public EventEmitter getEmitter() {
        return emitter;
    } 
    
    public interface Screen {
        public void handle(TouchPanelListener p, Event event);
    }
}