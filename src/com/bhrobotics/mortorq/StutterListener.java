package com.bhrobotics.mortorq;

import com.bhrobotics.morlib.Listener;
import com.bhrobotics.morlib.Event;
import com.bhrobotics.morlib.EventEmitter;
import com.bhrobotics.morlib.TimeoutEmitter;
import java.util.Hashtable;

public class StutterListener implements Listener {
    private EventEmitter emitter = new EventEmitter();
    private double highInterval;
    private double lowInterval;
    private int cycles;
    private TimeoutEmitter timeoutEmitter = new TimeoutEmitter();
    private AlternationListener altListener = new AlternationListener();
    private Hashtable highData;
    private Hashtable lowData;
    
    public StutterListener() {
        this(0.0, 0.0, 0);
    }
    
    public StutterListener(double h, double l, int c) {
        timeoutEmitter.bind("all", altListener);
        setHighInterval(h);
        setLowInterval(l);
        setCycles(c);
    }
    
    public EventEmitter getEmitter() {
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
        highData = event.getData();
        
        lowData = new Hashtable();
        lowData.put("oldValue", event.getData("newValue"));
        lowData.put("newValue", event.getData("oldValue"));
        
        emitter.trigger("update", highData);
        
        for (int i = 0; i < cycles; i++) {
            int j = i + 1;
            
            timeoutEmitter.schedule("low", (highInterval * j) + (lowInterval * i));
            timeoutEmitter.schedule("high", (highInterval * j) + (lowInterval * j));
        }
    }
    
    public void bound(String event, EventEmitter emitter) {}
    public void unbound(String event, EventEmitter emitter) {}
    
    private class AlternationListener implements Listener {
        public void handle(Event event) {
            System.out.println("altered");
            if (event.getName() == "low") {
                emitter.trigger("update", lowData);
            } else {
                emitter.trigger("update", highData);
            }
        }
        
        public void bound(String event, EventEmitter emitter) {}
        public void unbound(String event, EventEmitter emitter) {}
    }
}