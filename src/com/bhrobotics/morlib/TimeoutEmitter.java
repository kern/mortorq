package com.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

public class TimeoutEmitter extends EventEmitter {
    private EventEmitter process = Reactor.getInstance().getProcess();
    private Timer timer = new Timer();
    
    public void schedule(String event, double delay) {
        schedule(event, new Hashtable(), delay);
    }
    
    public void schedule(String event, Hashtable data, double delay) {
        TimeoutTask task = new TimeoutTask(event, data);
        timer.schedule(task, (long) (delay * 1000));
    }
    
    public void cancelAll() {
        timer.cancel();
        timer = new Timer();
    }
    
    private class TimeoutTask extends TimerTask {
        private String event;
        private Hashtable data;
        
        public TimeoutTask(String e, Hashtable d) {
            event = e;
            data = d;
        }
        
        public void run() {
            TimeoutEmitter.this.trigger(event, data);
        }
    }
}