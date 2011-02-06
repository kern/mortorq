package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Reactor;

public class TouchPanelListener implements Listener {
    private static final int NUM_SCREENS = 5;
    private boolean stopped              = false;
    private EventEmitter emitter         = new EventEmitter();
    private Screen[] screens             = new Screen[NUM_SCREENS];
    private Screen currentScreen;
    
    public TouchPanelListener() {
        screens[0]    = new StopScreen();
        currentScreen = screens[0];
    }
    
    public void handle(Event event) {
        if((event.getName()).equals("updateDigitals")) {
            if((((Short)(event.getData().get("newDigitals"))).shortValue() & 0x0001) == 0) {	
                Short digitals   = (Short) event.getData().get("newDigitals");
                int digits       = digitals.shortValue();
                int newScreenTag = ((digits & 0xC000) >> 14) + 1;
                Screen newScreen = screens[newScreenTag];
                
                if (newScreen != currentScreen) {
                    currentScreen = newScreen;
                }
            } else {
                currentScreen = screens[0];
                Reactor.getInstance().stopTicking();
            }
        } else {
            if(event.getName().startsWith("updateDigital")) {
                currentScreen.handle(emitter, event); 
            }
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
    
    public void bind(String event, Listener listener) {
        emitter.bind(event, listener);
    }
    
    public interface Screen {
        public void handle(EventEmitter emitter, Event event);
    }
    
    public class StopScreen implements Screen {
        private EventEmitter em;
        public boolean received;
        
        public void handle(EventEmitter em, Event e){
            // Ignore.
        }
    }
}