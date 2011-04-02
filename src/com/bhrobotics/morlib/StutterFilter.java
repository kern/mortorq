package com.bhrobotics.morlib;

import java.util.Hashtable;

public class StutterFilter extends EventEmitter implements Listener {
    private double highInterval;
    private double lowInterval;
    private int cycles;
    
    public StutterFilter() {
        this(0.0, 0.0, 0);
    }
    
    public StutterFilter(double h, double l, int c) {
        setHighInterval(h);
        setLowInterval(l);
        setCycles(c);
    }
    
    public double getHighInterval() {
        return highInterval;
    }
    
    public void setHighInterval(double h) {
        highInterval = h;
    }
    
    public double getLowInterval() {
        return lowInterval;
    }
    
    public void setLowInterval(double l) {
        lowInterval = l;
    }
    
    public int getCycles() {
        return cycles;
    }
    
    public void setCycles(int c) {
        cycles = c;
    }
    
    public void handle(Event highEvent) {
        cancelAll();
        String name = highEvent.getName();
        
        Hashtable lowData = new Hashtable();
        lowData.put("oldValue", highEvent.getData("newValue"));
        lowData.put("newValue", highEvent.getData("oldValue"));
        Event lowEvent = new Event(name, lowData);
        
        stutter(highEvent, lowEvent);
    }
    
    public void stutter(Event highEvent, Event lowEvent) {
        trigger(highEvent);
        
        for (int i = 0; i < cycles; i++) {
            int j = i + 1;
            
            schedule(lowEvent, (highInterval * j) + (lowInterval * i));
            schedule(highEvent, (highInterval * j) + (lowInterval * j));
        }
    }
}