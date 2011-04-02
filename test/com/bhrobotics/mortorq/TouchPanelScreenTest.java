package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Event;

public class TouchPanelScreenTest extends TestCase {
    StubScreen screen;
    
    public void setUp() {
        screen = new StubScreen();
    }
    
    public void testCtor() {
        assertNotNull(screen);
    }
    
    public void testPanel() {
        TouchPanelListener panel = new TouchPanelListener();
        assertNull(screen.getPanel());
        screen.setPanel(panel);
        assertSame(panel, screen.getPanel());
    }
    
    public void testBound() {
        assertEquals(0, screen.boundsReceived);
        assertEquals(0, screen.unboundsReceived);
        assertFalse(screen.isBound());
        
        screen.setBound(true);
        assertTrue(screen.isBound());
        assertEquals(1, screen.boundsReceived);
        assertEquals(0, screen.unboundsReceived);
        
        screen.setBound(true);
        assertTrue(screen.isBound());
        assertEquals(1, screen.boundsReceived);
        assertEquals(0, screen.unboundsReceived);
        
        screen.setBound(false);
        assertFalse(screen.isBound());
        assertEquals(1, screen.boundsReceived);
        assertEquals(1, screen.unboundsReceived);
        
        screen.setBound(false);
        assertFalse(screen.isBound());
        assertEquals(1, screen.boundsReceived);
        assertEquals(1, screen.unboundsReceived);
    }
    
    private class StubScreen extends TouchPanelScreen {
        public int boundsReceived = 0;
        public int unboundsReceived = 0;
        
        public void bound() {
            boundsReceived++;
        }
        
        public void unbound() {
            unboundsReceived++;
        }
    }
}