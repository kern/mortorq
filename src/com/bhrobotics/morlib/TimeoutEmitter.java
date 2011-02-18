package com.bhrobotics.morlib;

import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

public class TimeoutEmitter extends EventEmitter {
    private EventEmitter process = Reactor.getProcess();
    private Timer timer = new Timer();
    
    public void schedule(String event, double delay) {
        schedule(event, new Hashtable(), delay);
    }
    
    public void schedule(String event, Hashtable data, double delay) {
        schedule(new Event(event, data), delay);
    }
    
    public void schedule(Event event, double delay) {
        TimeoutTask task = new TimeoutTask(event);
        timer.schedule(task, (long) (delay * 1000));
    }
    
    public void cancelAll() {
        timer.cancel();
        timer = new Timer();
    }
    
    private class TimeoutTask extends TimerTask {
        private Event event;
        
        public TimeoutTask(Event e) {
            event = e;
        }
        
        public void run() {
            trigger(event);
        }
    }
}