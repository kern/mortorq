package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import java.util.Hashtable;

public class TouchPanelListenerTest extends TestCase {
    TouchPanelListener panel;
    
    public void setUp() {
        Reactor.getInstance().startTicking();
        panel = new TouchPanelListener();
    }
    
    public void tearDown() {
        Reactor.getInstance().stopTicking();
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
        screenChange1Data.put("oldDigitals", new Short((short) 0));
        screenChange1Data.put("newDigitals", new Short((short) 0x0001));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x0001));
        screenChange2Data.put("newDigitals", new Short((short) 0x0100));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        panel.handle(screenChange1Event);
        Reactor.getInstance().tick();
        
        assertSame(screen0, panel.getCurrentScreen());
        
        screen0.reset();
        
        panel.handle(screenChange2Event);
        Reactor.getInstance().tick();
        
        assertSame(screen1, panel.getCurrentScreen());
    }
    
    public void testScreenProxy() {
        StubScreen screen0 = new StubScreen("test0");
        StubScreen screen1 = new StubScreen("test1");
        panel.setScreen(0, screen0);
        panel.setScreen(1, screen1);
        
        Event otherEvent = new Event("foo", null);
        
        Hashtable screenChange1Data = new Hashtable();
        screenChange1Data.put("oldDigitals", new Short((short) 0x0000));
        screenChange1Data.put("newDigitals", new Short((short) 0x0001));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x0001));
        screenChange2Data.put("newDigitals", new Short((short) 0x0100));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        StubListener listener0 = new StubListener();
        StubListener listener1 = new StubListener();
        panel.getEmitter().bind("test0", listener0);
        panel.getEmitter().bind("test1", listener1);
        
        panel.handle(screenChange1Event);
        panel.handle(otherEvent);
        Reactor.getInstance().tick();
        
        assertTrue(screen0.received);
        assertTrue(listener0.received);
        assertFalse(screen1.received);
        assertFalse(listener1.received);
        
        screen0.reset();
        listener0.reset();
        
        panel.handle(screenChange2Event);
        panel.handle(otherEvent);
        Reactor.getInstance().tick();
        
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
        screenChange1Data.put("oldDigitals", new Short((short) 0x0100));
        screenChange1Data.put("newDigitals", new Short((short) 0x0200));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x0200));
        screenChange2Data.put("newDigitals", new Short((short) 0x0AA1));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        panel.handle(screenChange1Event);
        panel.handle(screenChange2Event);
        Reactor.getInstance().tick();
        
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
        
        public void bound(String event, EventEmitter emitter) {}
        public void unbound(String event, EventEmitter emitter) {}
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
                panel.getEmitter().trigger(name, event.getData());
            }
        }
        
        public void reset() {
            received = false;
        }
    }
}