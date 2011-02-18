package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Filter;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.TimeoutEmitter;
import java.util.Hashtable;

public class StutterFilter extends Filter {
    private TimeoutEmitter emitter = new TimeoutEmitter();
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
    
    public EventEmitter getEmitter() {
        return emitter;
    }
    
    public TimeoutEmitter getTimeoutEmitter() {
        return emitter;
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
        emitter.cancelAll();
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
            
            emitter.schedule(lowEvent, (highInterval * j) + (lowInterval * i));
            emitter.schedule(highEvent, (highInterval * j) + (lowInterval * j));
        }
    }
    
    public void cancelAll() {
        emitter.cancelAll();
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
}