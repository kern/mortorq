package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Event;

public abstract class TouchPanelScreen {
    protected TouchPanelFilter panel;
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