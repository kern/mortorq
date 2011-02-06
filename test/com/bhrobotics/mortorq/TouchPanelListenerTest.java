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
    
    public void testScreenChoose() {
        StubScreen screen1 = new StubScreen();
        StubScreen screen2 = new StubScreen();
        panel.addScreen(1, screen1);
        panel.addScreen(2, screen2);
        
        Event otherEvent = new Event("updateDigital5", null);
        
        Hashtable screenChange1Data = new Hashtable();
        screenChange1Data.put("oldDigitals", new Short((short) 0));
        screenChange1Data.put("newDigitals", new Short((short) 0x1544));
        
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        panel.handle(screenChange1Event);
        panel.handle(otherEvent);
        Reactor.getInstance().tick();
        
        assertTrue(screen1.received);
        assertFalse(screen2.received);
        assertSame(screen1.emitter, panel.getEmitter());
        assertSame(screen1, panel.getCurrentScreen());
        
        screen1.reset();
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x1544));
        screenChange2Data.put("newDigitals", new Short((short) 0x4236));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        panel.handle(screenChange2Event);
        panel.handle(otherEvent);
        Reactor.getInstance().tick();
        
        assertFalse(screen1.received);
        assertTrue(screen2.received);
        assertSame(screen2.emitter, panel.getEmitter());
        assertSame(screen2, panel.getCurrentScreen());
    }
    
    public void testScreen() {
        StubScreen screen1 = new StubScreen("test1");
        StubScreen screen2 = new StubScreen("test2");
        StubScreen screen3 = new StubScreen("test3");
        panel.addScreen(1, screen1);
        panel.addScreen(2, screen2);
        panel.addScreen(3, screen3);
        
        Event otherEvent = new Event("updateDigital1", null);
        
        Hashtable screenChange1Data = new Hashtable();
        screenChange1Data.put("oldDigitals", new Short((short) 0x0000));
        screenChange1Data.put("newDigitals", new Short((short) 0x1544));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x1544));
        screenChange2Data.put("newDigitals", new Short((short) 0x4236));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        Hashtable screenChange3Data = new Hashtable();
        screenChange3Data.put("oldDigitals", new Short((short) 0x4236));
        screenChange3Data.put("newDigitals", new Short((short) 0x8454));
        Event screenChange3Event = new Event("updateDigitals", screenChange3Data);
        
        StubListener listener1 = new StubListener();
        StubListener listener2 = new StubListener();
        StubListener listener3 = new StubListener();
        panel.getEmitter().bind("test1", listener1);
        panel.getEmitter().bind("test2", listener2);
        panel.getEmitter().bind("test3", listener3);
        
        panel.handle(screenChange1Event);
        panel.handle(otherEvent);
        Reactor.getInstance().tick();
        assertTrue(screen1.received);
        assertTrue(listener1.received);
        assertFalse(screen2.received);
        assertFalse(listener2.received);
        assertFalse(screen3.received);
        assertFalse(listener3.received);
        
        screen1.reset();
        listener1.reset();
        
        panel.handle(screenChange2Event);
        panel.handle(otherEvent);
        Reactor.getInstance().tick();
        assertFalse(screen1.received);
        assertFalse(listener1.received);
        assertTrue(screen2.received);
        assertTrue(listener2.received);
        assertFalse(screen3.received);
        assertFalse(listener3.received);
        
        screen2.reset();
        listener2.reset();
        
        panel.handle(screenChange3Event);
        panel.handle(otherEvent);
        Reactor.getInstance().tick();
        assertFalse(screen1.received);
        assertFalse(listener1.received);
        assertFalse(screen2.received);
        assertFalse(listener2.received);
        assertTrue(screen3.received);
        assertTrue(listener3.received);
    }
    
    public void testStop() {
        StubScreen screen = new StubScreen();
        StubListener listener = new StubListener();
        panel.addScreen(1, screen);
        
        Hashtable screenChange1Data = new Hashtable();
        screenChange1Data.put("oldDigitals", new Short((short) 0x0000));
        screenChange1Data.put("newDigitals", new Short((short) 0x4F9A));
        Event screenChange1Event = new Event("updateDigitals", screenChange1Data);
        
        Hashtable screenChange2Data = new Hashtable();
        screenChange2Data.put("oldDigitals", new Short((short) 0x2000));
        screenChange2Data.put("newDigitals", new Short((short) 0x3009));
        Event screenChange2Event = new Event("updateDigitals", screenChange2Data);
        
        panel.handle(screenChange1Event);
        panel.handle(screenChange2Event);
        Reactor.getInstance().tick();
        
        assertTrue(kill.received);
    }
    
    public void testBadScreenNumber() {
        TouchPanelListener panel = new TouchPanelListener();
        
        try {
            panel.addScreen(10, null);
            fail("Exepected Exception not thrown.");
        } catch (ArrayIndexOutOfBoundsException e) {
            // Ignore.
        }
    }
    
    public void testScreenAdd () {
        TouchPanelListener panel = new TouchPanelListener();
        int i1                   = 1;
        int i2                   = 2;
        StubScreen screen1       = new StubScreen();
        StubScreen screen2       = new StubScreen();
        
        panel.addScreen(i1, screen1);
        panel.addScreen(i2, screen2);
        
        assertSame(screen1, panel.getScreens()[i1]);
        assertSame(screen2, panel.getScreens()[i2]);			
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
        public void unbound(String event,EventEmitter emitter) {}
    }
    
    private class StubScreen implements TouchPanelListener.Screen {
        public boolean received = false;
        public EventEmitter emitter;
        private String name;
        
        public StubScreen() {}
        
        public StubScreen(String n) {
            name = n;
        }
        
        public void handle(EventEmitter em, Event event) {
            received = true;
            emitter = em;
            
            if (event.getName() == "updateDigital1") {
                emitter.trigger(name, event.getData());
            }
        }
        
        public void reset() {
            received = false;
            emitter = null;
        }
    }
}