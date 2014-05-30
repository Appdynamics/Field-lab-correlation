package com.appdynamics.selab.util;

public class CustomQueue {

    private Object[] queue;
    private int elementCount;

    public CustomQueue() {
        queue = new Object[10];
        elementCount = 0;
    }

    public void enqueue(Object o) {
        synchronized (queue) {
            while (elementCount == queue.length) {
                try {
                    queue.wait();
                } catch (InterruptedException ie) {
                    return;
                }
            }

            queue[elementCount++] = o;
            queue.notifyAll();
        }
    }

    public Object dequeue() {

        Object result = null;

        synchronized (queue) {
            while (elementCount == 0) {
                try {
                    queue.wait();
                } catch (InterruptedException ie) {
                    return null;
                }
            }

            result = queue[--elementCount];
            queue.notifyAll();
        }

        return result;
    }
}