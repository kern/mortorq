package com.bhrobotics.morlib;

public abstract class Reactor {
    private static ReactorInstance instance = ReactorInstance.getInstance();
    
    public static void startTicking() {
        instance.startTicking();
    }
    
    public static void stopTicking() {
        instance.stopTicking();
    }
    
    public static void tick() {
        instance.tick();
    }
    
    public static boolean isTicking() {
        return instance.isTicking();
    }
    
    public static synchronized void forceTick() {
        instance.forceTick();
    }
    
    public static Queue getQueue() {
        return instance.getQueue();
    }
    
    public static EventEmitter getProcess() {
        return instance.getProcess();
    }
}