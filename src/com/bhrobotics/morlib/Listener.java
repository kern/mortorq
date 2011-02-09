package com.bhrobotics.morlib;

public interface Listener {
    public void handle(Event event);
    public void bound(EventEmitter emitter, String event);
    public void unbound(EventEmitter emitter, String event);
}