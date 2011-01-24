package com.bhrobotics.morlib;

import java.util.Vector;

public class Reactor extends Thread {
    private boolean ticking         = false;
    private boolean forceTick       = false;
    private Queue queue             = new Queue();
    private EventEmitter process    = new EventEmitter();
    private static Reactor instance = new Reactor();
    
    private Reactor() {}
    
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
    
    public static Reactor getInstance() {
        return Reactor.instance;
    }
}