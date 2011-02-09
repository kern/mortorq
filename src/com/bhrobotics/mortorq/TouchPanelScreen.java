package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;

public abstract class TouchPanelScreen {
    private TouchPanelFilter panel;
    private boolean bound = false;
    
    public TouchPanelFilter getPanel() {
        return panel;
    }
    
    public void setPanel(TouchPanelFilter p) {
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