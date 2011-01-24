package com.bhrobotics.morlib;

import java.util.Hashtable;
import edu.wpi.first.wpilibj.Timer;

public class TimeoutEmitter extends EventEmitter {
    private EventEmitter process = Reactor.getInstance().getProcess();
    private Timer timer = new Timer();
    private TListener listener;
    private double timeout;
    private boolean periodic;
    
    public TimeoutEmitter() {
        listener = new TListener();
    }
    
    public double get() {
        return timer.get();
    }
    
    public double getTimeout() {
        return timeout;
    }
    
    public boolean isTimedOut() {
        return timer.get() >= timeout;
    }
    
    public void reset() {
        timer.reset();
    }
    
    public void start(double t) {
        start(t, false);
    }
    
    public void start(double t, boolean p) {
        timeout = t;
        periodic = p;
        process.bind("tick", listener);
        timer.start();
    }
    
    public void stop() {
        process.unbind("tick", listener);
        timer.stop();
        timer.reset();
    }
    
    public boolean isPeriodic() {
        return periodic;
    }
    
    private class TListener extends Listener {
        public void handle(Event event) {
            TimeoutEmitter emitter = TimeoutEmitter.this;
            
            if (emitter.isTimedOut()) {
                Hashtable data = new Hashtable();
                data.put("timeout", emitter);
                
                if(emitter.isPeriodic()) {
                    emitter.reset();
                } else {
                    emitter.stop();
                }
                
                emitter.trigger("timeout", data);
            }
        }
    }
}