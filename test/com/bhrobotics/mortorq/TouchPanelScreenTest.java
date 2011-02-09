package com.bhrobotics.mortorq;

import junit.framework.*;
import com.bhrobotics.morlib.Event;

public class TouchPanelScreenTest extends TestCase {
    TouchPanelScreen screen;
    
    public void setUp() {
        screen = new TouchPanelScreen() {
            public void handle(Event event) {}
        };
    }
    
    public void testCtor() {
        assertNotNull(screen);
    }
    
    public void testPanel() {
        TouchPanelFilter panel = new TouchPanelFilter();
        assertNull(screen.getPanel());
        screen.setPanel(panel);
        assertSame(panel, screen.getPanel());
    }
    
    public void testIsBound() {
        assertFalse(screen.isBound());
        screen.bound();
        assertTrue(screen.isBound());
        screen.bound();
        assertTrue(screen.isBound());
        screen.unbound();
        assertFalse(screen.isBound());
        screen.unbound();
        assertFalse(screen.isBound());
    }
}