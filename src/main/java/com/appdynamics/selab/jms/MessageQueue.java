package com.appdynamics.selab.jms;

import javax.jms.Queue;

public class MessageQueue implements Queue {

    public String getQueueName() {
        return "SE-LabOfDeath";
    }

    public String toString() {
        return "Queue: " + getQueueName();
    }
}
