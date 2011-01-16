package com.bhrobotics.morlib;

import java.util.Vector;

public class Reactor extends Thread {
    private boolean ticking      = false;
    private boolean forceTick    = false;
    private Queue queue          = new Queue();
    private EventEmitter process = new EventEmitter(queue);
    
    public synchronized void run() {
        while (true) {
            if (ticking || forceTick) {
                forceTick = false;
                
                tick();
            } else {
                try {
                    wait(500);
                } catch (InterruptedException e) {
                    // Ignored.
                }
            }
        }
    }
    
    public void startTicking() {
        getProcess().emit("start");
        ticking = true;
        forceTick();
    }
    
    public void stopTicking() {
        getProcess().emit("stop");
        ticking = false;
        forceTick();
    }
    
    public void tick() {
        getProcess().emit("tick");
        getProcess().emit("nextTick", true);
        getQueue().flush();
    }
    
    public boolean isTicking() {
        return ticking;
    }
    
    public synchronized void forceTick() {
        forceTick = true;
        notify();
    }
    
    public void setQueue(Queue q) {
        queue = q;
    }
    
    public Queue getQueue() {
        return queue;
    }
    
    public void setProcess(EventEmitter p) {
        process = p;
    }
    
    public EventEmitter getProcess() {
        return process;
    }
}
