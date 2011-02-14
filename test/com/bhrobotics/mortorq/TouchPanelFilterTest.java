package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import java.util.Hashtable;

// TODO: Negate all of the bitmasks.
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
        StubScreen screen2 = new StubScreen();
        StubScreen screen3 = new StubScreen();
        StubScreen screen4 = new StubScreen();
        panel.setScreen(0, screen0);
        panel.setScreen(1, screen1);
        panel.setScreen(2, screen2);
        panel.setScreen(3, screen3);
        panel.setScreen(4, screen4);
        
        panel.handle(screenChangeEvent(0));
        Reactor.tick();
        assertSame(screen0, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(1));
        Reactor.tick();
        assertSame(screen1, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(2));
        Reactor.tick();
        assertSame(screen2, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(3));
        Reactor.tick();
        assertSame(screen3, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(4));
        Reactor.tick();
        assertSame(screen4, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent((short) 0xABC8));
        Reactor.tick();
        assertSame(screen1, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent((short) 0xABCC));
        Reactor.tick();
        assertSame(screen2, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent((short) 0xABCA));
        Reactor.tick();
        assertSame(screen3, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent((short) 0xABCE));
        Reactor.tick();
        assertSame(screen4, panel.getCurrentScreen());
    }
    
    public void testScreenProxy() {
        StubScreen screen0 = new StubScreen("test0");
        StubScreen screen1 = new StubScreen("test1");
        panel.setScreen(0, screen0);
        panel.setScreen(1, screen1);
        
        Event otherEvent = new Event("foo", null);
        
        StubListener listener0 = new StubListener();
        StubListener listener1 = new StubListener();
        panel.bind("test0", listener0);
        panel.bind("test1", listener1);
        
        panel.handle(screenChangeEvent(0));
        panel.handle(otherEvent);
        Reactor.tick();
        
        assertTrue(screen0.received);
        assertTrue(listener0.received);
        assertFalse(screen1.received);
        assertFalse(listener1.received);
        
        screen0.reset();
        listener0.reset();
        
        panel.handle(screenChangeEvent(1));
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
        
        panel.handle(screenChangeEvent(0));
        Reactor.tick();
        assertSame(stopScreen, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(1));
        Reactor.tick();
        assertSame(otherScreen, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(0x000F));
        Reactor.tick();
        assertSame(stopScreen, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(0x0011));
        Reactor.tick();
        assertSame(stopScreen, panel.getCurrentScreen());
        
        panel.handle(screenChangeEvent(0xABCD));
        Reactor.tick();
        assertSame(stopScreen, panel.getCurrentScreen());
    }
    
    private Event screenChangeEvent(int number) {
        switch (number) {
        case 0:
            return screenChangeEvent((short) 0x0001);
        case 1:
            return screenChangeEvent((short) 0x0000);
        case 2:
            return screenChangeEvent((short) 0x0004);
        case 3:
            return screenChangeEvent((short) 0x0002);
        case 4:
            return screenChangeEvent((short) 0x0006);
        default:
            return screenChangeEvent((short) 0x000F);
        }
    }
    
    private Event screenChangeEvent(short tag) {
        Hashtable data = new Hashtable();
        data.put("oldDigitals", new Short((short) 0x0001));
        data.put("newDigitals", new Short(tag));
        return new Event("updateDigitals", data);
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