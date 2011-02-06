package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.TimeoutEmitter;
import java.util.Hashtable;

public class StutterListener implements Listener {
    private TimeoutEmitter emitter = new TimeoutEmitter();
    private double highInterval;
    private double lowInterval;
    private int cycles;
    
    public StutterListener() {
        this(0.0, 0.0, 0);
    }
    
    public StutterListener(double h, double l, int c) {
        setHighInterval(h);
        setLowInterval(l);
        setCycles(c);
    }
    
    public EventEmitter getEmitter() {
        return (EventEmitter) emitter;
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
        Hashtable highData = event.getData();
        
        Hashtable lowData = new Hashtable();
        lowData.put("oldValue", event.getData("newValue"));
        lowData.put("newValue", event.getData("oldValue"));
        
        emitter.trigger("update", highData);
        
        for (int i = 0; i < cycles; i++) {
            int j = i + 1;
            
            emitter.schedule("update", lowData, (highInterval * j) + (lowInterval * i));
            emitter.schedule("update", highData, (highInterval * j) + (lowInterval * j));
        }
    }
    
    public void bound(String event, EventEmitter emitter) {}
    public void unbound(String event, EventEmitter emitter) {}
}