package com.bhrobotics.morlib;

public abstract class Listener {
    protected Reactor reactor = Reactor.getInstance();
    protected EventEmitter process = reactor.getProcess();
    public abstract void handle(Event event);
}