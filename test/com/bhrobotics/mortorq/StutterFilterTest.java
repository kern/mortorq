package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Reactor;
import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import java.util.Hashtable;
import java.util.Vector;

public class StutterFilterTest extends TestCase {
    public void setUp() {
        Reactor.startTicking();
    }
    
    public void tearDown() {
        Reactor.stopTicking();
    }
    
    public void testCtor() {
        StutterFilter stutter = new StutterFilter();
        assertNotNull(stutter);
    }
    
    public void testTimeoutEmitter() {
        StutterFilter stutter = new StutterFilter();
        assertNotNull(stutter.getTimeoutEmitter());
    }
    
    public void testIntervals() {
        StutterFilter stutter = new StutterFilter();
        assertEquals(0.0, stutter.getHighInterval(), 0.05);
        assertEquals(0.0, stutter.getLowInterval(), 0.05);
        
        stutter = new StutterFilter(7.0, 8.0, 0);
        assertEquals(7.0, stutter.getHighInterval(), 0.05);
        assertEquals(8.0, stutter.getLowInterval(), 0.05);
        
        stutter.setHighInterval(2.0);
        assertEquals(2.0, stutter.getHighInterval(), 0.05);
        
        stutter.setLowInterval(5.0);
        assertEquals(5.0, stutter.getLowInterval(), 0.05);
    }
    
    public void testCycles() {
        StutterFilter stutter = new StutterFilter();
        assertEquals(0, stutter.getCycles());
        
        stutter = new StutterFilter(7.0, 8.0, 6);
        assertEquals(6, stutter.getCycles());
        
        stutter.setCycles(9);
        assertEquals(9, stutter.getCycles());
    }
    
    public void testHandle() {
        StutterFilter stutter = new StutterFilter(0.01, 0.01, 2);
        StubListener listener = new StubListener();
        stutter.bind("update", listener);
        
        Hashtable data = new Hashtable();
        data.put("oldValue", new Boolean(false));
        data.put("newValue", new Boolean(true));
        Event event = new Event("update", data);
        
        stutter.handle(event);
        sleep(500);
        assertCycles(event, listener, 2);
        
        listener.reset();
        stutter.setCycles(10);
        
        stutter.handle(event);
        sleep(500);
        assertCycles(event, listener, 10);
    }
    
    public void testInterrupt() {
        StutterFilter stutter = new StutterFilter(0.1, 0.1, 2);
        StubListener listener = new StubListener();
        stutter.bind("interruptMe", listener);
        
        Hashtable data = new Hashtable();
        data.put("oldValue", new Boolean(false));
        data.put("newValue", new Boolean(true));
        Event event = new Event("interruptMe", data);
        
        stutter.handle(event);
        assertEquals(1, listener.calls);
        stutter.handle(event);
        sleep(600);
        assertEquals(6, listener.calls);
    }
    
    public void testTiming() {
        StutterFilter stutter = new StutterFilter(0.1, 0.2, 1);
        StubListener listener = new StubListener();
        stutter.bind("update", listener);
        
        Hashtable data = new Hashtable();
        data.put("oldValue", new Boolean(false));
        data.put("newValue", new Boolean(true));
        Event event = new Event("update", data);
        
        stutter.handle(event);
        sleep(10);
        assertEquals(1, listener.calls);
        sleep(200);
        assertEquals(2, listener.calls);
        sleep(50);
        assertEquals(2, listener.calls);
        sleep(100);
        assertEquals(3, listener.calls);
        sleep(300);
        assertEquals(3, listener.calls);
    }
    
    private void assertCycles(Event event, StubListener listener, int cycles) {
        int toggles = 1 + (cycles * 2);
        assertEquals(toggles, listener.calls);
        
        // Check to make sure the value is toggling.
        for (int i = 0; i < toggles; i++) {
            boolean isEven = i % 2 == 0;
            Hashtable data = (Hashtable) listener.data.elementAt(i);
            boolean oldValue = ((Boolean) data.get("oldValue")).booleanValue();
            boolean newValue = ((Boolean) data.get("newValue")).booleanValue();
            
            if (isEven) {
                assertFalse(oldValue);
                assertTrue(newValue);
            } else {
                assertTrue(oldValue);
                assertFalse(newValue);
            }
        }
    }
    
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            // Ignore.
        }
    }
    
    private class StubListener implements Listener {
        public int calls = 0;
        public Vector data = new Vector();
        
        public void handle(Event event) {
            calls++;
            data.addElement(event.getData());
        }
        
        public void bound(EventEmitter emitter, String event) {}
        public void unbound(EventEmitter emitter, String event) {}
        
        public void reset() {
            calls = 0;
            data.removeAllElements();
        }
    }
}