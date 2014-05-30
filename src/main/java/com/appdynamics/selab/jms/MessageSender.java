package com.appdynamics.selab.jms;

import javax.jms.QueueSender;
import javax.jms.Queue;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.JMSException;

public class MessageSender implements QueueSender {

    private Queue queue;
    private SimpleQueueReceiver cheating;

    public MessageSender(Queue queue, SimpleQueueReceiver cheating) {
        this.queue = queue;
        this.cheating = cheating;
    }

    public void	close() {}

    public int	getDeliveryMode() { return 1; }

    public Destination	getDestination() { return getQueue(); }

    public boolean	getDisableMessageID() { return false; }

    public boolean	getDisableMessageTimestamp() { return false; }

    public int	getPriority() { return 1; }

    public long	getTimeToLive() { return Long.MAX_VALUE; }

    public void	send(Queue queue, Message message, int deliveryMode, int priority, long timeToLive) {
        send((Destination) queue, message, deliveryMode, priority, timeToLive);
    }

    public void	send(Destination destination, Message message) {
        send(destination, message, 1, 1, Long.MAX_VALUE);
    }

    public void	send(Queue queue, Message message) {
        send((Destination) queue, message, 1, 1, Long.MAX_VALUE);
    }

    public void	send(Destination destination, Message message, int deliveryMode, int priority, long timeToLive) {
        try {
            message.setJMSDestination(destination);
        } catch (JMSException notGonnaHappen) {

        }

        cheating.enqueue(message);
    }

    public void	send(Message message) {
        send(getQueue(), message);
    }

    public void	send(Message message, int deliveryMode, int priority, long timeToLive) {
        send(getQueue(), message, deliveryMode, priority, timeToLive);
    }

    public void	setDeliveryMode(int deliveryMode) {}

    public void	setDisableMessageID(boolean value) {}

    public void	setDisableMessageTimestamp(boolean value) {}

    public void	setPriority(int defaultPriority) {}

    public void	setTimeToLive(long timeToLive) {}

    public Queue getQueue() {
        return queue;
    }
}
