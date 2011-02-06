package com.bhrobotics.morlib;

import java.util.Vector;
import java.util.Enumeration;

public class Queue extends Vector {
    public QueueItem getHead() {
        QueueItem item = (QueueItem) firstElement();
        removeElement(item);
        return item;
    }
    
    public void addHead(Event event, Listener listener) {
        addHead(new QueueItem(event, listener));
    }
    
    public void addHead(QueueItem item) {
        insertElementAt(item, 0);
    }
    
    public QueueItem getTail() {
        QueueItem item = (QueueItem) lastElement();
        removeElement(item);
        return item;
    }
    
    public void addTail(Event event, Listener listener) {
        addTail(new QueueItem(event, listener));
    }
    
    public void addTail(QueueItem item) {
        addElement(item);
    }
    
    public void clear() {
        removeAllElements();
    }
    
    public synchronized void flush() {
        QueueItem[] currentTickItems = new QueueItem[size()];
        copyInto(currentTickItems);
        clear();
        
        for(int i = 0; i < currentTickItems.length; i++) {
            QueueItem item = currentTickItems[i];
            item.handle();
        }
    }
}