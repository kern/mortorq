package com.bhrobotics.morlib;

public class QueueItem {
    private Event event;
    private Listener listener;
    
    public QueueItem(Event e, Listener l) {
        event = e;
        listener = l;
    }
    
    public Event getEvent() {
        return event;
    }
    
    public Listener getListener() {
        return listener;
    }
}