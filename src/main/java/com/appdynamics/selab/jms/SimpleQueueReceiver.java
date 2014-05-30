package com.appdynamics.selab.jms;

import javax.jms.QueueReceiver;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;

public class SimpleQueueReceiver implements QueueReceiver {

    private Message[] mQueue;
    private int messageCount;
    private Queue queue;

    public SimpleQueueReceiver(Queue queue) {
        mQueue = new Message[10];
        messageCount = 0;
        this.queue = queue;
    }

    public void close() {}

    public MessageListener getMessageListener() { return null; }

    public String getMessageSelector() { return null; }

    public Message receive() {
        return receive(0L);
    }

    public Message receive(long timeout) {
        // ignore the timeout
        Message result = null;

        synchronized (mQueue) {
            while (messageCount == 0) {
                try {
                    mQueue.wait();
                } catch (InterruptedException ie) {
                    return null;
                }
            }

            result = mQueue[--messageCount];
            mQueue.notifyAll();
        }

        return result;
    }

    public Message receiveNoWait() {
        return null;
    }

    public void setMessageListener(MessageListener listener) {}

    public Queue getQueue() {
        return queue;
    }

    public void enqueue(Message m) {
        synchronized (mQueue) {
            while (messageCount == mQueue.length) {
                try {
                    mQueue.wait();
                } catch (InterruptedException ie) {
                    return;
                }
            }

            mQueue[messageCount++] = m;
            mQueue.notifyAll();
        }
    }
}