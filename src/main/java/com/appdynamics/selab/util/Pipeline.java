package com.appdynamics.selab.util;

public class Pipeline {

    private Object[] pipe;
    private int elementCount;

    public Pipeline() {
        pipe = new Object[10];
        elementCount = 0;
    }

    public void put(Object o) {
        synchronized (pipe) {
            while (elementCount == pipe.length) {
                try {
                    pipe.wait();
                } catch (InterruptedException ie) {
                    return;
                }
            }

            pipe[elementCount++] = o;
            pipe.notifyAll();
        }
    }

    public Object take() {

        Object result = null;

        synchronized (pipe) {
            while (elementCount == 0) {
                try {
                    pipe.wait();
                } catch (InterruptedException ie) {
                    return null;
                }
            }

            result = pipe[--elementCount];
            pipe.notifyAll();
        }

        return result;
    }
}