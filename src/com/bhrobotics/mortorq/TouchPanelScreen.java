package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;

public abstract class TouchPanelScreen {
    private TouchPanelListener panel;
    private boolean bound = false;
    
    public TouchPanelListener getPanel() {
        return panel;
    }
    
    public void setPanel(TouchPanelListener p) {
        panel = p;
    }
    
    public boolean isBound() {
        return bound;
    }
    
    public void bound() {
        bound = true;
    }
    
    public void unbound() {
        bound = false;
    }
    
    public abstract void handle(Event event);
}