package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import java.util.Hashtable;

public class TouchPanelFilterTest extends TestCase {
    TouchPanelFilter panel;
    
    public void setUp() {
        Reactor.startTicking();
        panel = new TouchPanelFilter();
    }
    
    public void tearDown() {
        Reactor.stopTicking();
    }
    
    public void testCtor() {
        assertNotNull(panel);
    }
    
    public void testScreen() {
        StubScreen screen = new StubScreen();
        assertNull(panel.getScreen(0));
        panel.setScreen(0, screen);
        assertSame(screen, panel.getScreen(0));
        assertSame(panel, screen.getPanel());
    }
    
    public void testBadScreenTag() {
        StubScreen screen = new StubScreen();
        
        try {
            panel.setScreen(10, screen);
            fail("Expected ArrayIndexOutOfBoundsException not thrown.");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Ignore.
        }
    }
    
    public void testCurrentScreen() {
        StubScreen screen0 = new StubScreen();
        StubScreen screen1 = new StubScreen();
        panel.setScreen(0, screen0);
        panel.setScreen(1, screen1);
        
        assertNull(panel.getCurrentScreen());
        assertFalse(screen0.isBound());
        assertFalse(screen1.isBound());
        
        panel.setCurrentScreen(0);
        
        assertSame(screen0, panel.getCurrentScreen());
        assertTrue(screen0.isBound());
        assertFalse(screen1.isBound());
        
        panel.setCurrentScreen(1);
        
        assertSame(screen1, panel.getCurrentScreen());
        assertFalse(screen0.isBound());
        assertTrue(screen1.isBound());
    }
    
    public void testScreenChoose() {
        StubScreen screen0 = new StubScreen();
        StubScreen screen1 = new StubScreen();
        panel.setScreen(0, screen0);
        panel.setScreen(1, screen1);
        
        Hashtable screenChange1Data = new Hashtable();
        screenChange1Data.put("oldDigitals", new Short((short) 0x0AA0));
        screenChange1Data.put("newDigitals", new Short((short) 0x0001));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x0001));
        screenChange2Data.put("newDigitals", new Short((short) 0x0000));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        panel.handle(screenChange1Event);
        Reactor.tick();
        
        assertSame(screen0, panel.getCurrentScreen());
        
        screen0.reset();
        
        panel.handle(screenChange2Event);
        Reactor.tick();
        
        assertSame(screen1, panel.getCurrentScreen());
    }
    
    public void testScreenProxy() {
        StubScreen screen0 = new StubScreen("test0");
        StubScreen screen1 = new StubScreen("test1");
        panel.setScreen(0, screen0);
        panel.setScreen(1, screen1);
        
        Event otherEvent = new Event("foo", null);
        
        Hashtable screenChange1Data = new Hashtable();
        screenChange1Data.put("oldDigitals", new Short((short) 0x0002));
        screenChange1Data.put("newDigitals", new Short((short) 0x0001));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x0001));
        screenChange2Data.put("newDigitals", new Short((short) 0x0000));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        StubListener listener0 = new StubListener();
        StubListener listener1 = new StubListener();
        panel.bind("test0", listener0);
        panel.bind("test1", listener1);
        
        panel.handle(screenChange1Event);
        panel.handle(otherEvent);
        Reactor.tick();
        
        assertTrue(screen0.received);
        assertTrue(listener0.received);
        assertFalse(screen1.received);
        assertFalse(listener1.received);
        
        screen0.reset();
        listener0.reset();
        
        panel.handle(screenChange2Event);
        panel.handle(otherEvent);
        Reactor.tick();
        
        assertFalse(screen0.received);
        assertFalse(listener0.received);
        assertTrue(screen1.received);
        assertTrue(listener1.received);
    }
    
    public void testStop() {
        StubScreen stopScreen = new StubScreen();
        StubScreen otherScreen = new StubScreen();
        panel.setScreen(0, stopScreen);
        panel.setScreen(1, otherScreen);
        
        Hashtable screenChange1Data = new Hashtable();
        screenChange1Data.put("oldDigitals", new Short((short) 0x0AA0));
        screenChange1Data.put("newDigitals", new Short((short) 0x0000));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x0000));
        screenChange2Data.put("newDigitals", new Short((short) 0x0003));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        panel.handle(screenChange1Event);
        panel.handle(screenChange2Event);
        Reactor.tick();
        
        assertSame(stopScreen, panel.getCurrentScreen());
    }
    
    private class StubListener implements Listener {
        public boolean received = false;
        
        public void handle(Event e) {
            received = true;
        }
        
        public void reset() {
            received = false;
        }
        
        public void bound(EventEmitter emitter, String event) {}
        public void unbound(EventEmitter emitter, String event) {}
    }
    
    private class StubScreen extends TouchPanelScreen {
        public boolean received = false;
        private String name;
        
        public StubScreen() {}
        
        public StubScreen(String n) {
            name = n;
        }
        
        public void handle(Event event) {
            received = true;
            
            if (event.getName() == "foo") {
                panel.trigger(name, event.getData());
            }
        }
        
        public void reset() {
            received = false;
        }
    }
}