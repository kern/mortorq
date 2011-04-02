package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;

public abstract class TouchPanelScreen {
    protected TouchPanelListener panel;
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
    
    public void setBound(boolean b) {
        if (b && !bound) {
            bound = b;
            bound();
        } else if (!b && bound) {
            bound = b;
            unbound();
        }
    }
    
    public void handle(Event event) {}
    public void bound() {}
    public void unbound() {}
}