package com.bhrobotics.morlib;

public interface Listener {
    public void handle(Event event);
    public void bound(String event, EventEmitter emitter);
    public void unbound(String event, EventEmitter emitter);
}