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
        StubScreen screen1 = new StubScreen(null);
        StubScreen screen2 = new StubScreen(null);
        Hashtable fullData = new Hashtable();
        
        fullData.put("oldDigitals", new Short((short) 0));
        fullData.put("newDigitals", new Short((short) 0x1544));
        Hashtable data = new Hashtable();
        data.put("oldValue", new Boolean(false));
        data.put("newValue", new Boolean(true)); 
        Event event       = new Event("updateDigitals", fullData);
        Event screenEvent = new Event("updateDigital5", data);
        panel.addScreen(1, screen1);
        panel.addScreen(2, screen2);
        
        panel.handle(event);
        panel.handle(screenEvent);
        Reactor.getInstance().tick();
        
        assertTrue(screen1.received);
        assertFalse(screen2.received);
        assertSame(screen1.emitter, panel.getEmitter());
        assertSame(screen1, panel.getCurrentScreen());
        
        Hashtable fullData2 = new Hashtable();
        fullData2.put("oldDigitals", new Short((short) 0x1544));
        fullData2.put("newDigitals", new Short((short) 0x4236));
        Event event2 = new Event("updateDigitals", fullData2);
        screen1.received = false;
        
        panel.handle(event2);
        panel.handle(screenEvent);
        Reactor.getInstance().tick();
        
        assertTrue(screen2.received);
        assertFalse(screen1.received);
        assertSame(screen2.emitter, panel.getEmitter());
        assertSame(screen2, panel.getCurrentScreen());
    }
    
    public void testTouchScreenBind() {
        StubListener stub = new StubListener();
        panel.bind("test1", stub);
        assertTrue(stub.bound); 
    }
    
    public void testScreen() {
        StubScreen screen1       = new StubScreen("test1");
        StubScreen screen2       = new StubScreen("test2");
        StubScreen screen3       = new StubScreen("test3");
        StubListener listener    = new StubListener();
        StubListener listener2   = new StubListener();
        StubListener listener3   = new StubListener();
        Hashtable data           = new Hashtable();
        data.put("oldValue" , new Boolean(false));
        data.put("newValue", new Boolean(true));
        Hashtable fullData = new Hashtable();
        fullData.put("oldDigitals", new Short((short) 0x0000));
        fullData.put("newDigitals", new Short((short) 0x4F9A));
        Hashtable fullData2 = new Hashtable();
        fullData2.put("oldDigitals", new Short((short) 0x4F9A));
        fullData2.put("newDigitals", new Short((short) 0x2F34));
        Hashtable fullData3 = new Hashtable();
        fullData3.put("oldDigitals", new Short((short) 0x4F34));
        fullData3.put("newDigitals", new Short((short) 0x8454));
        panel.addScreen(1, screen1);
        panel.addScreen(2, screen2);
        panel.addScreen(3, screen3);
        Event screenChangeEvent  = new Event("updateDigitals", fullData);
        Event screenChangeEvent2 = new Event("updateDigitals", fullData2);
        Event screenChangeEvent3 = new Event("updateDigitals", fullData3); 
        Event screenEvent        = new Event("updateDigital1", data);
        panel.bind("test1", listener);
        panel.bind("test2", listener2);
        panel.bind("test3", listener3);
        
        panel.handle(screenEvent);
        panel.handle(screenChangeEvent);
        panel.handle(screenEvent);
        panel.handle(screenChangeEvent2);
        panel.handle(screenEvent);
        panel.handle(screenChangeEvent3);
        panel.handle(screenEvent);
        Reactor.getInstance().tick();
        
        assertTrue(screen1.received);
        assertTrue(screen2.received);
        assertTrue(listener.received);
        assertTrue(listener2.received);	
        assertTrue(listener3.received);
    }
    
    public void testStop() {
        StubScreen screen   = new StubScreen(null);
        StopListener kill   = new StopListener();
        Hashtable fullData2 = new Hashtable();
        
        fullData2.put("oldDigitals", new Short((short) 0x0000));
        fullData2.put("newDigitals", new Short((short) 0x4F9A));
        Hashtable fullData = new Hashtable();
        
        fullData.put("oldDigitals", new Short((short) 0x0000));
        fullData.put("newDigitals", new Short((short) 0x2000));
        Hashtable stopData = new Hashtable();
        
        stopData.put("oldDigitals", new Short((short) 0x2000));
        stopData.put("newDigitals", new Short((short) 0x3009));	
        Event stop = new Event("updateDigitals", stopData);
        
        Reactor.getInstance().getProcess().bind("stop", kill);		
        
        panel.handle(new Event("updateDigitals", fullData));
        panel.handle(new Event ("updateDigitals", fullData2));
        panel.handle(stop);
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
        StubScreen screen1       = new StubScreen(null);
        StubScreen screen2       = new StubScreen(null);
        
        panel.addScreen(i1, screen1);
        panel.addScreen(i2, screen2);
        
        assertSame(screen1, panel.getScreens()[i1]);
        assertSame(screen2, panel.getScreens()[i2]);			
    }
    
    private class StubListener implements Listener {
        public boolean received;
        private boolean bound;
        private EventEmitter emit;
        
        public void handle(Event e) {
            received = true;
        }
        
        public void bound(String event, EventEmitter emitter) {
            bound = true;
        }
        
        public void unbound(String event,EventEmitter emitter) {}
    }
    
    private class StopListener implements Listener {
        public boolean received;
        private boolean bound;
        private EventEmitter emitter;
        
        public void handle(Event e) {
            received = true;
        }
        
        public void bound(String event, EventEmitter emitter) {
            bound = true;
        }
        
        public void unbound(String event, EventEmitter emitter) {}
    }
    
    private class StubScreen implements TouchPanelListener.Screen {
        public boolean received;
        public EventEmitter emitter;
        private String name;
        
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
    }
}