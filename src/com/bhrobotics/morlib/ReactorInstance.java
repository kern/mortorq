package com.bhrobotics.morlib;

import java.util.Vector;

public class ReactorInstance extends Thread {
    private boolean ticking          = false;
    public boolean forceTick         = false;
    private Queue queue              = new Queue();
    private EventEmitter process     = new EventEmitter();
    private static ReactorInstance instance = new ReactorInstance();
    
    private ReactorInstance() {
        start();
    }
    
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
        process.trigger("start");
        ticking = true;
        forceTick();
    }
    
    public void stopTicking() {
        process.trigger("stop");
        ticking = false;
        forceTick();
    }
    
    public void tick() {
        process.trigger("tick");
        process.trigger("nextTick", true);
        queue.flush();
    }
    
    public boolean isTicking() {
        return ticking;
    }
    
    public synchronized void forceTick() {
        forceTick = true;
        notify();
    }
    
    public Queue getQueue() {
        return queue;
    }
    
    public EventEmitter getProcess() {
        return process;
    }
    
    public static ReactorInstance getInstance() {
        return instance;
    }
}
