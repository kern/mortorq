package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Reactor;
import java.util.Hashtable;

public class TouchPanelListenerTest extends TestCase {
    public void suiteSetUp() {
        Reactor.getInstance().start();
    }
    public void setUp() {
        Reactor.getInstance().startTicking();
    }
    
    public void tearDown() {
        Reactor.getInstance().stopTicking();
    }
    
    public void testCtor() {
        TouchPanelListener screen = new TouchPanelListener();
        Assert.assertNotNull(screen);
    }
    
    public void testScreenChoose() {
        TouchPanelListener panel = new TouchPanelListener();
        StubScreen screen1 = new StubScreen();
        StubScreen screen2 = new StubScreen();
        Hashtable data = new Hashtable();
        data.put("oldDigitals", new Short((short) 0));
        data.put("newDigitals", new Short((short) 0x8000));
        Event event = new Event("updateDigitals", data);
        Event screenEvent = new Event("updateDigital5", data);
        panel.addScreen(4, screen1);
        panel.addScreen(5, screen2);
        
        panel.handle(event);
        panel.handle(screenEvent);
        Reactor.getInstance().tick();
        
        Assert.assertTrue(screen1.received);
        Assert.assertFalse(screen2.received);
        
        data.put("oldDigitals", new Short((short) 0x8000));
        data.put("newDigitals", new Short((short) 0xA000));
        screen1.received = false;
        
        panel.handle(event);
        panel.handle(screenEvent);
        Reactor.getInstance().tick();

        Assert.assertTrue(screen2.received);
        Assert.assertFalse(screen1.received);
    }
    
    public void testBadScreenNumber() {
        TouchPanelListener panel = new TouchPanelListener();
        try {
            panel.addScreen(10,null);
            Assert.fail("Exepected Exception not thrown.");
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Ignore.
        }
    }
    
    private class StubListener extends Listener {
        public boolean received;
        
        public void handle(Event e) {
            received = true;
        }
    }
    
    private class StubScreen implements TouchPanelListener.Screen {
        public boolean received;

        public void handle(TouchPanelListener p, Event e) {
            received = true;
        } 
    }
}