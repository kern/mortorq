package com.bhrobotics.morlib;

public abstract class Listener {
    protected Reactor reactor = Reactor.getInstance();
    protected EventEmitter process = reactor.getProcess();
    public void handle(Event event) {}
    public void bound(String event, EventEmitter emitter) {}
    public void unbound(String event, EventEmitter emitter) {}
}