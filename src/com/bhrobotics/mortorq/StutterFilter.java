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
    
    public void handle(Event event) {
        emitter.cancelAll();
        String name = event.getName();
        Hashtable highData = event.getData();
        
        Hashtable lowData = new Hashtable();
        lowData.put("oldValue", event.getData("newValue"));
        lowData.put("newValue", event.getData("oldValue"));
        
        trigger(name, highData);
        
        for (int i = 0; i < cycles; i++) {
            int j = i + 1;
            
            emitter.schedule(name, lowData, (highInterval * j) + (lowInterval * i));
            emitter.schedule(name, highData, (highInterval * j) + (lowInterval * j));
        }
    }
    
    public void bound(EventEmitter emitter, String event) {}
    public void unbound(EventEmitter emitter, String event) {}
}